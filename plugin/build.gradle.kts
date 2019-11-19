import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-gradle-plugin`
    `kotlin-dsl`
    `maven-publish`
    id("com.gradle.plugin-publish") version "0.10.0"
}

group = "com.louiscad.splitties"
version = "0.1.6"

gradlePlugin {
    plugins {
        create("com.louiscad.splitties") {
            id = "com.louiscad.splitties"
            @Suppress("UnstableApiUsage")
            displayName = "Splitties"
            @Suppress("UnstableApiUsage")
            description = "A family of small Kotlin libraries for delightful Android development\n"
            implementationClass = "com.louiscad.splitties.SplittiesPlugin"
        }
    }
}

repositories {
    mavenCentral()
    jcenter()
    gradlePluginPortal()
}

pluginBundle {
    website = "https://github.com/LouisCAD/Splitties"
    vcsUrl = "https://github.com/LouisCAD/Splitties"
    tags = listOf("android", "kotlin")
}
dependencies {
    implementation("de.fayard:refreshVersions:0.8.4")
    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.4.0")
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
    val experimentalAnnotations = listOf("kotlin.Experimental")
    kotlinOptions.freeCompilerArgs = experimentalAnnotations.map { "-Xuse-experimental=$it"}
}

tasks.withType<Test>().configureEach {
    @Suppress("UnstableApiUsage")
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
