/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("SpellCheckingInspection")

import Build_gradle.ArtifactGroupNaming.*

// Top-level build file where you can add configuration options common to all sub-projects/modules.

task<Delete>("clean") {
    delete(rootProject.buildDir)
}

val versionPlaceholder = "_"

allprojects {
    tasks.whenTaskAdded {
        if("DebugUnitTest" in name || "ReleaseUnitTest" in name) {
            enabled = false
            // MPP + Android unit testing is so broken we just disable it altogether,
            // (discussion here https://kotlinlang.slack.com/archives/C3PQML5NU/p1572168720226200)
        }
    }
    repositories { setupForProject() }
    configurations.all { setupVersionPlaceholdersResolving() }
    //TODO: Replace code below by pluginManagement or something else?
    // (it is incorrect, because it runs once classpath configuration is already resolved)
    //buildscript.configurations.all { setupVersionPlaceholdersResolving() }
}

tasks.register("checkDependenciesUpdates") {
    doFirst {
        val allConfigurations: Set<Configuration> = allprojects.flatMap {
            it.configurations + it.buildscript.configurations
        }.toSet()
        val allDependencies = allConfigurations.asSequence()
            .flatMap { it.allDependencies.asSequence() }
            .distinctBy { it.group + it.name }
        //TODO: Filter using known grouping strategies to only use the main artifact to resolve latest version, this
        // will improve performance.
        val dependenciesToUpdate: Sequence<Pair<Dependency, String>> = allDependencies.mapNotNull { dependency ->
            val latestVersion = getLatestDependencyVersion(dependency) ?: return@mapNotNull null
            val usedVersion = dependency.version
            if (usedVersion == latestVersion) null else dependency to latestVersion
        }
        //TODO: Write updates to gradle.properties without overwriting unrelated properties, comments and structure.
        dependenciesToUpdate.forEach { (dependency: Dependency, latestVersion: String) ->
            println("Dependency ${dependency.group}:${dependency.name}:${dependency.version} -> $latestVersion")
        }
    }
}

//TODO: Allow to get if release stability level between Stable, RC, M(ilestone), eap, beta, alpha, dev and unknown,
// then allow setting default level and per group/artifact exceptions. Maybe through comments in gradle.properties?
fun isVersionStable(version: String): Boolean {
    //TODO: cache list and regex for improved efficiency.
    val hasStableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^[0-9,.v-]+$".toRegex()
    return hasStableKeyword || regex.matches(version)
}

fun Project.getLatestDependencyVersion(dependency: Dependency): String? {
    val tmpDependencyUpdateConfiguration = configurations.create("getLatestVersion") {
        dependencies.add(dependency)
        resolutionStrategy.componentSelection.all {
            if (isVersionStable(candidate.version).not()) reject("Unstable version")
        }
        resolutionStrategy.eachDependency {
            if (requested.version != null) useVersion("+")
        }
    }
    try {
        val lenientConfiguration = tmpDependencyUpdateConfiguration.resolvedConfiguration.lenientConfiguration
        return lenientConfiguration.getFirstLevelModuleDependencies(Specs.SATISFIES_ALL).singleOrNull()?.moduleVersion
    } finally {
        configurations.remove(tmpDependencyUpdateConfiguration)
    }
}


fun Configuration.setupVersionPlaceholdersResolving() {
    resolutionStrategy.eachDependency {
        if (requested.version != versionPlaceholder) return@eachDependency
        useVersion(requested.getVersionFromProperties())
    }
}

fun ModuleVersionSelector.getVersionFromProperties(): String {
    val moduleIdentifier: ModuleIdentifier = try {
        @Suppress("UnstableApiUsage")
        module
    } catch (e: Throwable) { // Guard against possible API changes.
        println(e)
        object : ModuleIdentifier {
            override fun getGroup(): String = this@getVersionFromProperties.group
            override fun getName(): String = this@getVersionFromProperties.name
        }
    }
    val propertyName = getVersionPropertyName(moduleIdentifier)
    val version = property(propertyName = propertyName)
    return version as String
}

fun getVersionPropertyName(moduleIdentifier: ModuleIdentifier): String {
    val group = moduleIdentifier.group
    val name = moduleIdentifier.name
    val versionKey: String = when (moduleIdentifier.findArtifactGroupingRule()?.groupNaming) {
        GroupOnly -> group
        GroupLastPart -> group.substringAfterLast('.')
        GroupFirstTwoParts -> {
            val groupFirstPart = group.substringBefore('.')
            val groupSecondPart = group.substringAfter('.').substringBefore('.')
            "$groupFirstPart.$groupSecondPart"
        }
        GroupFirstThreeParts -> {
            val groupFirstPart = group.substringBefore('.')
            val groupSecondPart = group.substringAfter('.').substringBefore('.')
            val groupThirdPart = group.substringAfter('.').substringAfter('.').substringBefore('.')
            "$groupFirstPart.$groupSecondPart.$groupThirdPart"
        }
        GroupAndNameFirstPart -> "$group.${name.substringBefore('-')}"
        GroupLastPartAndNameSecondPart -> {
            val groupLastPart = group.substringAfterLast('.')
            val nameSecondPart = name.substringAfter('-').substringBefore('-')
            "$groupLastPart.$nameSecondPart"
        }
        GroupFirstPartAndNameTwoFirstParts -> {
            val groupFirstPart = group.substringBefore('.')
            val nameFirstPart = name.substringBefore('-')
            val nameSecondPart = name.substringAfter('-').substringBefore('-')
            "$groupFirstPart.$nameFirstPart-$nameSecondPart"
        }
        null -> "$group..$name"
    }
    return "version.$versionKey"
}

fun ModuleIdentifier.findArtifactGroupingRule(): ArtifactGroupingRule? {
    if (forceFullyQualifiedName(this)) return null
    val fullArtifactName = "$group:$name"
    return artifactsGroupingRules.find { fullArtifactName.startsWith(it.artifactNamesStartingWith) }
}

fun forceFullyQualifiedName(moduleIdentifier: ModuleIdentifier): Boolean {
    val group = moduleIdentifier.group
    val name = moduleIdentifier.name
    if (group.startsWith("androidx.") && group != "androidx.legacy") {
        val indexOfV = name.indexOf("-v")
        if (indexOfV != -1 &&
            indexOfV < name.lastIndex &&
            name.substring(indexOfV + 1, name.lastIndex).all { it.isDigit() }
        ) return true // AndroidX artifacts ending in "v18" or other "v${someApiLevel}" have standalone version number.
    }
    return false
}

val artifactsGroupingRules: List<ArtifactGroupingRule> = sequenceOf(
    "org.jetbrains.kotlin:kotlin" to GroupLastPart,
    "org.jetbrains.kotlinx:kotlinx" to GroupLastPartAndNameSecondPart,
    "androidx." to GroupOnly,
    "androidx.media:media-widget" to GroupFirstPartAndNameTwoFirstParts,
    "androidx.test:core" to GroupAndNameFirstPart, // Rest of androidx.test share the same version.
    "androidx.test.ext:junit" to GroupAndNameFirstPart,
    "androidx.test.ext:truth" to GroupFirstTwoParts, // Same version as the rest of androidx.test.
    "androidx.test.services" to GroupFirstTwoParts, // Same version as the rest of androidx.test.
    "androidx.test.espresso.idling" to GroupFirstThreeParts, // Same version as other androidx.test.espresso artifacts.
    "com.louiscad.splitties:splitties" to GroupLastPart,
    "com.squareup.retrofit2" to GroupLastPart,
    "com.squareup.okhttp3" to GroupLastPart,
    "com.squareup.moshi" to GroupLastPart,
    "com.squareup.sqldelight" to GroupLastPart,
    "org.robolectric" to GroupLastPart
).map { (artifactNamesStartingWith, groupNaming) ->
    ArtifactGroupingRule(
        artifactNamesStartingWith = artifactNamesStartingWith,
        groupNaming = groupNaming
    )
}.sortedByDescending { it.artifactNamesStartingWith.length }.toList()

/**
 * We assume each part of the "group" is dot separated (`.`), and each part of the name is dash separated (`-`).
 */
sealed class ArtifactGroupNaming {
    object GroupOnly : ArtifactGroupNaming()
    object GroupLastPart : ArtifactGroupNaming()
    object GroupFirstTwoParts : ArtifactGroupNaming()
    object GroupFirstThreeParts : ArtifactGroupNaming()
    object GroupAndNameFirstPart : ArtifactGroupNaming()
    object GroupLastPartAndNameSecondPart : ArtifactGroupNaming()
    object GroupFirstPartAndNameTwoFirstParts : ArtifactGroupNaming()
}

class ArtifactGroupingRule(
    val artifactNamesStartingWith: String,
    val groupNaming: ArtifactGroupNaming
) {
    init {
        require(artifactNamesStartingWith.count { it == ':' } <= 1)
    }
}
