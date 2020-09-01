/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

import java.io.File

println("Let's migrate the gradle files of this project from Groovy to Kotlin.")
val dir = File(".").parentFile!!
val ignoredRootDirNames =
    listOf("build", "buildSrc", "gradle", "old-dot-gradle", "projectFilesBackup")

val moduleDirectories: List<File> = dir.listFiles { file: File ->
    file.isDirectory &&
            !file.name.startsWith('.') &&
            file.name !in ignoredRootDirNames &&
            file.listFiles { it: File -> it.isDirectory && it.name == "src" }.size == 1
}!!.sortedBy { it.name }

val nonModuleDirNames = listOf("src", "build", "libs")

fun File.findGradleBuildFiles(): List<File> = listFiles { file: File ->
    file.name == "build.gradle"
}.asList() + listFiles { file: File ->
    file.isDirectory && !file.name.startsWith('.') && file.name !in nonModuleDirNames
}.flatMap { it.findGradleBuildFiles() }

val gradleBuildFiles: List<File> = moduleDirectories.flatMap {
    it.findGradleBuildFiles()
} + (dir.resolve("build.gradle").takeIf { it.exists() }?.let { listOf(it) } ?: emptyList())

sealed class DslNode(val content: Content) {
    class Content(
        val symbolsReplaces: List<Pair<String, String>> = emptyList(),
        val functionCalls: List<String> = emptyList(),
        val mutableProperties: List<String> = emptyList(),
        val mutableLists: List<String> = emptyList(),
        val mutableSets: List<String> = emptyList(),
        val mutableMaps: List<String> = emptyList(),
        val nodes: List<DslNode> = emptyList()
    ) {
        operator fun plus(other: Content): Content = Content(
            functionCalls = functionCalls + other.functionCalls,
            mutableProperties = mutableProperties + other.mutableProperties,
            nodes = nodes + other.nodes
        )
    }

    class Named(val name: String, content: Content) : DslNode(content)

    class NamedDomainObjectContainer(
        val name: String,
        val existingNames: List<String> = emptyList(),
        content: Content
    ) : DslNode(content)
}

class PluginDefinition(
    val applyName: String,
    val pluginDslCode: String,
    val content: DslNode.Content
)

//region Definitions (groovy symbols disambiguation).
private object Definitions {

    @Suppress("SpellCheckingInspection")
    private val coreExternalNativeOptions = DslNode.Content(
        symbolsReplaces = listOf("cFlags" to "getcFlags()"),
        mutableLists = listOf("arguments", "getcFlags()", "cppFlags"),
        mutableSets = listOf("abiFilters", "targets")
    )
    private val externalNativeBuildNode = DslNode.Named(
        name = "externalNativeBuild",
        content = DslNode.Content(
            functionCalls = listOf("_initWith"),
            nodes = listOf(
                DslNode.Named(name = "cmake", content = coreExternalNativeOptions),
                DslNode.Named(name = "ndkBuild", content = coreExternalNativeOptions)
            )
        )
    )
    @Suppress("SpellCheckingInspection")
    private val ndkNode = DslNode.Named(
        name = "ndk",
        content = DslNode.Content(
            functionCalls = listOf(
                "abiFilter",
                "abiFilters",
                "ldLibs",
                "setLdLibs",
                "setcFlags"
            )
        )
    )
    @Suppress("SpellCheckingInspection")
    private val shadersNode = DslNode.Named(
        name = "shaders",
        content = DslNode.Content(
            functionCalls = listOf("glslcArgs", "glslcScopedArgs")
        )
    )

    private val baseConfigImplContent = DslNode.Content(
        functionCalls = listOf(
            "addBuildConfigField",
            "addBuildConfigFields",
            "addManifestPlaceholders",
            "addResValue",
            "addResValues"
        ),
        mutableProperties = listOf(
            "applicationIdSuffix",
            "manifestPlaceholders",
            "multiDexEnabled",
            "multiDexKeepFile",
            "multiDexKeepProguard",
            "versionNameSuffix"
        )
    )
    @Suppress("SpellCheckingInspection")
    private val defaultBuildTypeContent = DslNode.Content(
        functionCalls = listOf(),
        mutableProperties = listOf(
            "isDebuggable",
            "isJniDebuggable",
            "isRenderscriptDebuggable",
            "isCrunchPngs",
            "isEmbedMicroApp",
            "isPseudoLocalesEnabled",
            "isTestCoverageEnabled",
            "isZipAlignEnabled",
            "renderscriptOptimLevel"
        )
    ) + baseConfigImplContent
    @Suppress("SpellCheckingInspection")
    private val buildTypeContent = DslNode.Content(
        functionCalls = listOf(
            "buildConfigField",
            "consumerProguardFile",
            "consumerProguardFiles",
            "initWith",
            "proguardFile",
            "proguardFiles",
            "resValue",
            "testProguardFile",
            "testProguardFiles"
        ),
        mutableProperties = listOf(
            "isCrunchPngs",
            "isUseProguard",
            "isMinifyEnabled",
            "matchingFallbacks",
            "isShrinkResources"
        ),
        nodes = listOf(
            externalNativeBuildNode,
            ndkNode,
            DslNode.Named(
                "postprocessing", DslNode.Content(
                    functionCalls = listOf(
                        "consumerProguardFile",
                        "consumerProguardFiles",
                        "initWith",
                        "proguardFile",
                        "proguardFiles",
                        "testProguardFile",
                        "testProguardFiles"
                    ),
                    mutableProperties = listOf(
                        "codeShrinker",
                        "isObfuscate",
                        "isOptimizeCode",
                        "isRemoveUnusedCode",
                        "isRemoveUnusedResources"
                    )
                )
            ),
            shadersNode
        )
    ) + defaultBuildTypeContent

    @Suppress("SpellCheckingInspection")
    private val defaultProductFlavorContent = DslNode.Content(
        functionCalls = listOf(
            "addResourceConfiguration",
            "addResourceConfigurations",
            "mergeApplicationIdSuffix",
            "mergeVersionNameSuffix",
            "missingDimensionStrategy"
        ),
        mutableProperties = listOf(
            "applicationId",
            "dimension",
            "maxSdkVersion",
            "renderscriptNdkModeEnabled",
            "renderscriptSupportModeBlasEnabled",
            "renderscriptSupportModeEnabled",
            "renderscriptTargetApi",
            "resourceConfigurations",
            "signingConfig",
            "testApplicationId",
            "testInstrumentationRunner",
            "testInstrumentationRunnerArguments",
            "versionCode",
            "versionName",
            "wearAppUnbundled"
        )
    ) + baseConfigImplContent

    private val baseFlavorContent = DslNode.Content(
        functionCalls = listOf(
            "buildConfigField",
            "consumerProguardFile",
            "consumerProguardFiles",
            "maxSdkVersion",
            "minSdkVersion",
            "proguardFile",
            "proguardFiles",
            "resConfig",
            "resConfigs",
            "resValue",
            "targetSdkVersion",
            "testInstrumentationRunnerArgument",
            "testInstrumentationRunnerArguments",
            "testProguardFile",
            "testProguardFiles",
            "wearAppUnbundled"
        ),
        nodes = listOf(
            ndkNode,
            externalNativeBuildNode,
            DslNode.Named(
                name = "javaCompileOptions",
                content = DslNode.Content(
                    nodes = listOf(
                        DslNode.Named(
                            name = "annotationProcessorOptions",
                            content = DslNode.Content(
                                functionCalls = listOf(
                                    "_initWith",
                                    "argument",
                                    "arguments",
                                    "className",
                                    "classNames",
                                    "compilerArgumentProvider",
                                    "compilerArgumentProviders"
                                ),
                                mutableProperties = listOf("includeCompileClasspath")
                            )
                        )
                    )
                )
            ),
            shadersNode,
            DslNode.Named(
                name = "vectorDrawables",
                content = DslNode.Content(
                    functionCalls = listOf("generatedDensities"),
                    mutableProperties = listOf("useSupportLibrary")
                )
            )
        )
    ) + defaultProductFlavorContent

    private val splitOptionsContent = DslNode.Content(
        functionCalls = listOf("exclude", "include"),
        mutableProperties = listOf("isEnabled")
    )

    @Suppress("SpellCheckingInspection")
    private val androidBaseExtensionContent = DslNode.Content(
        functionCalls = listOf(
            "addVariant",
            "compileSdkVersion",
            "deviceProvider",
            "flavorDimensions",
            "generatePureSplits",
            "getDefaultProguardFile",
            "registerArtifactType",
            "registerBuildTypeSourceProvider",
            "registerJavaArtifact",
            "registerMultiFlavorSourceProvider",
            "registerProductFlavorSourceProvider",
            "registerTransform",
            "resourcePrefix",
            "testServer",
            "useLibrary"
        ),
        mutableProperties = listOf(
            "buildToolsVersion",
            "defaultPublishConfig",
            "generatePureSplits",
            "ndkVersion",
            "variantFilter"
        ),
        nodes = listOf(
            DslNode.Named(
                "aaptOptions", DslNode.Content(
                    functionCalls = listOf(
                        "additionalParameters",
                        "noCompress"
                    ),
                    mutableProperties = listOf(
                        "cruncherEnabled",
                        "cruncherProcesses",
                        "failOnMissingConfigEntry",
                        "ignoreAssets",
                        "ignoreAssetsPattern",
                        "namespaced",
                        "useNewCruncher"
                    )
                )
            ),
            DslNode.Named(
                "adbOptions", DslNode.Content(
                    functionCalls = listOf("installOptions"),
                    mutableProperties = listOf("timeOutInMs")
                )
            ),
            DslNode.NamedDomainObjectContainer(
                "buildTypes", existingNames = listOf("debug", "release"), content = buildTypeContent
            ),
            DslNode.Named(
                "compileOptions", DslNode.Content(
                    symbolsReplaces = listOf("incremental" to "setIncremental"),
                    functionCalls = listOf("setIncremental"),
                    mutableProperties = listOf(
                        "encoding",
                        "sourceCompatibility",
                        "targetCompatibility"
                    )
                )
            ),
            DslNode.Named(
                "dataBinding", DslNode.Content(
                    mutableProperties = listOf(
                        "isEnabled",
                        "isEnabledForTests",
                        "addDefaultAdapters",
                        "version"
                    )
                )
            ),
            DslNode.Named("defaultConfig", baseFlavorContent),
            DslNode.Named(
                "dexOptions", DslNode.Content(
                    mutableProperties = listOf(
                        "additionalParameters",
                        "dexInProcess",
                        "javaMaxHeapSize",
                        "jumboMode",
                        "keepRuntimeAnnotatedClasses",
                        "maxProcessCount",
                        "preDexLibraries",
                        "threadCount"
                    )
                )
            ),
            DslNode.Named(
                "externalNativeBuild", DslNode.Content(
                    nodes = listOf(
                        DslNode.Named(name = "cmake", content = coreExternalNativeOptions),
                        DslNode.Named(name = "ndkBuild", content = coreExternalNativeOptions)
                    )
                )
            ),
            DslNode.Named(
                "lintOptions", DslNode.Content(
                    functionCalls = listOf(
                        "baseline",
                        "check",
                        "disable",
                        "enable",
                        "error",
                        "fatal",
                        "ignore",
                        "informational",
                        "textOutput",
                        "warning"
                    ),
                    mutableProperties = listOf(
                        "isAbortOnError",
                        "isAbsolutePaths",
                        "baselineFile",
                        "check",
                        "isCheckAllWarnings",
                        "isCheckDependencies",
                        "isCheckGeneratedSources",
                        "isCheckReleaseBuilds",
                        "isCheckTestSources",
                        "disable",
                        "enable",
                        "isExplainIssues",
                        "htmlOutput",
                        "htmlReport",
                        "isIgnoreTestSources",
                        "isIgnoreWarnings",
                        "lintConfig",
                        "isNoLines",
                        "isQuiet",
                        "isShowAll",
                        "textReport",
                        "isWarningsAsErrors",
                        "xmlOutput",
                        "xmlReport"
                    )
                )
            ),
            DslNode.Named(
                "packagingOptions", DslNode.Content(
                    functionCalls = listOf(
                        "doNotStrip",
                        "exclude",
                        "merge",
                        "pickFirst"
                    ),
                    mutableProperties = listOf(
                        "doNotStrip",
                        "excludes",
                        "merges",
                        "pickFirsts"
                    )
                )
            ),
            DslNode.NamedDomainObjectContainer(
                "productFlavors", content = DslNode.Content(
                    functionCalls = listOf("setMatchingFallbacks"),
                    mutableProperties = listOf("matchingFallbacks"),
                    mutableLists = listOf("matchingFallbacks")
                ) + baseFlavorContent
            ),
            DslNode.NamedDomainObjectContainer(
                "signingConfigs", content = DslNode.Content(
                    functionCalls = listOf("initWith"),
                    mutableProperties = listOf(
                        "isV1SigningEnabled",
                        "isV2SigningEnabled",
                        "storeFile",
                        "keyAlias",
                        "keyPassword",
                        "storePassword",
                        "storeType"
                    )
                )
            ),
            DslNode.NamedDomainObjectContainer(
                "sourceSets", existingNames = listOf("main", "debug", "release"),
                content = DslNode.Content()
            ),
            DslNode.Named(
                "splits", content = DslNode.Content(
                    mutableProperties = listOf(
                        "abiFilters",
                        "densityFilters",
                        "languageFilters"
                    ),
                    nodes = listOf(
                        DslNode.Named(
                            "abi", DslNode.Content(
                                mutableProperties = listOf("isUniversalApk")
                            ) + splitOptionsContent
                        ),
                        DslNode.Named(
                            "density", DslNode.Content(
                                functionCalls = listOf("compatibleScreens"),
                                mutableProperties = listOf("isStrict")
                            ) + splitOptionsContent
                        ),
                        DslNode.Named(
                            "language", DslNode.Content(
                                functionCalls = listOf("include")
                            ) + splitOptionsContent
                        )
                    )
                )
            ),
            DslNode.Named(
                "testOptions", content = DslNode.Content(
                    mutableProperties = listOf(
                        "animationsDisabled",
                        "execution",
                        "reportDir",
                        "resultsDir"
                    )
                )
            ),
            DslNode.Named(
                "variantFilter", content = DslNode.Content(
                    functionCalls = listOf("setIgnore")
                )
            )
        )
    )

    private val androidTestedExtensionContent = DslNode.Content(
        functionCalls = listOf("addTestVariant", "addUnitTestVariant"),
        mutableProperties = listOf("testBuildType")
    ) + androidBaseExtensionContent

    val pluginDefinitions: List<PluginDefinition> = listOf(
        PluginDefinition(
            applyName = "com.android.application",
            pluginDslCode = "id(\"com.android.application\")",
            content = DslNode.Content(
                nodes = listOf(
                    DslNode.Named(
                        name = "android",
                        content = DslNode.Content(
                            functionCalls = listOf(),
                            mutableProperties = listOf(),
                            nodes = listOf(
                                DslNode.Named(
                                    "bundle", DslNode.Content(
                                        nodes = listOf(
                                            DslNode.Named(
                                                "abi", DslNode.Content(
                                                    mutableProperties = listOf("enableSplit")
                                                )
                                            ),
                                            DslNode.Named(
                                                "density", DslNode.Content(
                                                    mutableProperties = listOf("enableSplit")
                                                )
                                            ),
                                            DslNode.Named(
                                                "language", DslNode.Content(
                                                    mutableProperties = listOf("enableSplit")
                                                )
                                            )
                                        ),
                                        mutableSets = listOf("dynamicFeatures")
                                    )
                                )
                            )
                        ) + androidTestedExtensionContent
                    )
                )
            )
        ),
        PluginDefinition(
            applyName = "com.android.library",
            pluginDslCode = "id(\"com.android.library\")",
            content = DslNode.Content(
                nodes = listOf(
                    DslNode.Named(
                        name = "android",
                        content = DslNode.Content(
                            functionCalls = listOf(
                                "addVariant",
                                "aidlPackageWhiteList",
                                "packageBuildConfig"
                            )
                        ) + androidTestedExtensionContent
                    )
                )
            )
        )
    )
}
//endregion

private val lineDelimiters = listOf("\r\n", "\n", "\r")

fun makeQuotesKotlinCompatible(input: String): String {
    var index = 0
    return buildString(capacity = input.length) {
        append(input)
        outerLoop@ while (index < input.length) {
            when (input[index]) {
                '\\' -> index++
                '\'' -> set(index, '\"')
                '/' -> when (input[index + 1]) {
                    '/' -> {
                        val indexOfNextLine = input.indexOfAny(lineDelimiters, index)
                        if (indexOfNextLine == -1) {
                            return@buildString
                        } else {
                            index = indexOfNextLine
                        }
                    }
                    '*' -> { // Groovy doesn't support nested block comments.
                        index += 2
                        blockCommentLoop@ while (index < input.length) {
                            val indexOfNextBlockCommentEnd = input.indexOf("*/", index)
                            when (indexOfNextBlockCommentEnd) {
                                -1 -> return@buildString
                                else -> {
                                    index = indexOfNextBlockCommentEnd + 2
                                    continue@outerLoop
                                }
                            }
                        }
                    }
                }
                '"' -> {
                    index++
                    while (index < input.length) {
                        val indexOfClosingDoubleQuote = input.indexOf('"', index)
                        index = indexOfClosingDoubleQuote + 1
                        if (input[indexOfClosingDoubleQuote - 1] != '\\') continue@outerLoop
                    }
                }
            }
            index++
        }
    }
}

/** Returns false if block comment doesn't end or line comment doesn't end with line break. */
fun String.isLineOrBlockComment(): Boolean {
    if (length < 2) return false
    if (this[0] != '/') return false
    val blockComment = this[1] == '*'
    return (blockComment || this[1] == '/') &&
            if (blockComment) endsWith("*/") else lineDelimiters.any { endsWith(it) }
}

sealed class ScriptRegion(val value: String) {
    class StringLiteralLessCode(value: String) : ScriptRegion(value)
    class StringLiteral(value: String) : ScriptRegion(value) {
        init {
            require(value.first() == '"' && value.last() == '"' && value.length >= 2) {
                "String literal needs to include the quotes."
            }
        }
    }

    class Comment(value: String) : ScriptRegion(value)

    companion object {
        val nonCodeRegionStart = listOf("\"", "//", "/*")
    }
}

sealed class CodeOrComment(val value: String) {
    class Code(value: String) : CodeOrComment(value)
    class Comment(value: String) : CodeOrComment(value)
    companion object {
        val commentStart = listOf("//", "/*")
    }
}

fun splitScriptInRegions(input: String): List<ScriptRegion> {
    val regions = mutableListOf<ScriptRegion>()
    var index = 0
    val nonCodeRegionStart = ScriptRegion.nonCodeRegionStart
    outerLoop@ while (index < input.length) {
        val nonCodeStartIndex = input.indexOfAny(nonCodeRegionStart, startIndex = index)
        if (nonCodeStartIndex == -1) {
            regions += ScriptRegion.StringLiteralLessCode(input.substring(index))
            break@outerLoop
        }
        val codeFragment = input.substring(startIndex = index, endIndex = nonCodeStartIndex)
        regions += ScriptRegion.StringLiteralLessCode(codeFragment)
        when {
            input.startsWith("\"", startIndex = nonCodeStartIndex) -> {//TODO: Ignore escaped quotes
                val endIndex = input.indexOf("\"", startIndex = nonCodeStartIndex + 1)
                require(endIndex != -1) {
                    "The string literal has seemingly no end! " +
                            "Note that escaped quotes are not supported yet."
                }
                index = endIndex + 1
                val stringLiteral =
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                regions += ScriptRegion.StringLiteral(stringLiteral)
            }
            input.startsWith("//", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOfAny(lineDelimiters, startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    val eolLength = if (input.startsWith("\r\n", startIndex = endIndex)) 2 else 1
                    index = endIndex + eolLength
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += ScriptRegion.Comment(comment)
            }
            input.startsWith("/*", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOf("*/", startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    index = endIndex + 2
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += ScriptRegion.Comment(comment)
            }
            else -> throw IllegalStateException()
        }
    }
    return regions
}

fun splitCommentsFromCode(input: String): List<CodeOrComment> {
    val regions = mutableListOf<CodeOrComment>()
    var index = 0
    val commentStart = CodeOrComment.commentStart
    outerLoop@ while (index < input.length) {
        val nonCodeStartIndex = input.indexOfAny(commentStart, startIndex = index)
        if (nonCodeStartIndex == -1) {
            val remainingCode = input.substring(index)
            if (remainingCode.isNotEmpty()) regions += CodeOrComment.Code(remainingCode)
            break@outerLoop
        }
        val codeFragment = input.substring(startIndex = index, endIndex = nonCodeStartIndex)
        if (codeFragment.isNotEmpty()) regions += CodeOrComment.Code(codeFragment)
        when {
            input.startsWith("//", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOfAny(lineDelimiters, startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    val eolLength = if (input.startsWith("\r\n", startIndex = endIndex)) 2 else 1
                    index = endIndex + eolLength
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += CodeOrComment.Comment(comment)
            }
            input.startsWith("/*", startIndex = nonCodeStartIndex) -> {
                val endIndex = input.indexOf("*/", startIndex = nonCodeStartIndex + 2)
                val comment = if (endIndex == -1) input.substring(nonCodeStartIndex) else {
                    index = endIndex + 2
                    input.substring(startIndex = nonCodeStartIndex, endIndex = index)
                }
                regions += CodeOrComment.Comment(comment)
            }
            else -> throw IllegalStateException()
        }
    }
    return regions
}

/** Expects output from [makeQuotesKotlinCompatible]. */
fun replaceCodeFromString(input: String, oldValue: String, newValue: String): String {
    return buildString {
        splitCommentsFromCode(input).forEach { region ->
            when (region) {
                is CodeOrComment.Code -> {
                    append(region.value.replace(oldValue, newValue))
                }
                else -> append(region.value)
            }
        }
    }
}

println()
println()
TODO()

/**
 * A model that keeps the [localValues] defined in the current scope, and the [indent] if new ones
 * need to be added.
 */
class ScopeData(val indent: String, val localValues: List<String>)

fun migrateToKotlinWithNodeData(
    input: List<CodeOrComment>,
    scopeData: ScopeData,
    nodeContent: DslNode.Content
): String {
    var inputIndex = 0
    val output = StringBuilder()
    while (inputIndex < input.size) {
        val codeOrComment = input[inputIndex]
        when (codeOrComment) {
            is CodeOrComment.Comment -> {
                output.append(codeOrComment.value)
                inputIndex++
            }
            is CodeOrComment.Code -> {
                val code = codeOrComment.value
                val indexOfFirstThing = code.indexOfFirst { it.isWhitespace().not() }
                output.append(code.substring(startIndex = 0, endIndex = indexOfFirstThing))
                TODO("What should we do here?")
                var index = 0
                while (index < code.length) {
                    TODO()
                    index++
                }
            }
        }
    }
    return output.toString()
}

fun migrateToKotlin(
    input: List<CodeOrComment>,
    scopeData: ScopeData,
    node: DslNode.Named
): String {
    TODO()
}

fun migrateToKotlin(
    input: List<CodeOrComment>,
    scopeData: ScopeData,
    node: DslNode.NamedDomainObjectContainer
): String {
    TODO()
}

object GradleGroovyToKotlinMigrator {
    @Suppress("UNREACHABLE_CODE", "UNUSED_PARAMETER")//TODO: Remove when fully implemented
    fun migrateFile(file: File) {
        TODO("Skip migration if unknown plugins are found")
        TODO("Perform a git mv before writing the text (IMPORTANT, test this also.)")
        TODO("Similar to AndroidX migration")
    }

    private fun convertGroovyScriptToKotlin(source: String): String {
        var code = source
        var step = 0
        while (true) {
            step++
            code = when (step) {
                1 -> TODO()//makeQuotesKotlinCompatible(code)
                2 -> TODO("Replace all def by val or var")
                3 -> TODO("Find all ext properties defined in root project's gradle file and replace them plus their usages in scopes.")
                else -> return code
            }
        }
    }

    sealed class ScriptNode { // Does it really need to be sealed and have different subclasses?
        abstract fun generateKotlinVersion(): String
        sealed class Parent(val nodes: List<ScriptNode>) : ScriptNode() {
            init {
                TODO()
            }
        }

        class WhitespaceOrComments(val string: String) : ScriptNode() {
            override fun generateKotlinVersion() = string
        }
    }

    /**
     * Expects quotes to be Kotlin compatible already.
     */
    @Suppress("UNREACHABLE_CODE", "UNUSED_PARAMETER")//TODO: Remove when fully implemented
    private fun migrateSymbolsSyntax(input: String, extProperties: List<String>): String {
        ' '.isWhitespace()
        TODO("Have a model (put in a collection or nested collections) that encapsulates…")
        TODO("braces blocks, indent, comments, symbols and parameters (with their type or declaration source if possible)")
        TODO("Migrate apply plugin to plugins DSL and remaining to apply block")
        TODO("Migrate nodes by splitting content into a list of instances of a sealed class for comments, code, and more")
        TODO("and migrate ext properties per node in this same loop")
        TODO("Handle if, else if, else, and maybe while, for, and maybe switch too.")
        TODO("Handle 'is' prefixed properties which don't have the prefix in groovy (and first letter case)")
        TODO("Handle properties with collection literal value and replace them with mapOf or listOf")
        TODO("Keep track of ext properties, and locals/variables already declared in scope (or parent) to not redeclare them")
        TODO("Support flat DSL usage (and partly-flat)")
    }
}
