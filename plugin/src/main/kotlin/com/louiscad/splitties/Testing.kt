package com.louiscad.splitties

object Testing {
    private const val strikt_version = "+"
    private const val mockk_version = "+"
    private const val mockitokotlin_version = "+"
    private const val mockito_version = "+" // https://github.com/mockito/mockito/releases
    private const val junitJupiterVersion = "+"
    private const val spek_version = "+"
    private const val kotestVersion = "+"
    private const val junit4_version = "+"

    private const val kotestArtifact = "io.kotestArtifact:kotestArtifact-"

    /***
     * JUnit is a simple framework to write repeatable tests. It is an instance of the xUnit architecture for unit testing frameworks.

     * https://junit.org/junit4/
     */
    const val junit4 = "junit:junit:$junit4_version"

    /**
     * The new major version of the programmer-friendly testing framework for Java
     * https://junit.org/junit5/
     */
    const val junitJupiterApi = "org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion"

    /**
     * The new major version of the programmer-friendly testing framework for Java
     * https://junit.org/junit5/
     */
    const val junitJupiterParams = "org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion"

    /**
     * The new major version of the programmer-friendly testing framework for Java
     * https://junit.org/junit5/
     */
    const val junitJupiterEnding = "org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion"


    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestRunner = "$kotestArtifact-runner-junit5:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestRunnerConsole = "$kotestArtifact-assertions-runner-console:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestAssertions = "$kotestArtifact-assertions:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestAssertionsArrow = "$kotestArtifact-assertions-arrow:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestExtensions = "$kotestArtifact-assertions-extensions:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestSpring = "$kotestArtifact-assertions-extensions-spring:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestKtor = "$kotestArtifact-assertions-extensions-ktor:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestKoin = "$kotestArtifact-assertions-extensions-koin:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestJson = "$kotestArtifact-assertions-extensions-json:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestSystem = "$kotestArtifact-assertions-extensions-system:$kotestVersion"
    /**
     * Powerful, elegant and flexible test framework for Kotlin
     * https://github.com/kotlintest/kotlintest
     * **/
    const val kotestDatagen = "$kotestArtifact-datagen:$kotestVersion"



    /** https://www.spekframework.org **/
    const val spekDslJvm = "org.spekframework.spek2:spek-dsl-jvm:$spek_version"
    /** https://www.spekframework.org **/
    const val spekRunner = "org.spekframework.spek2:spek-runner-junit5:$spek_version"

    /**
     * Tasty mocking framework for unit tests in Java
     * https://site.mockito.org/
     */
    const val mockitoCore = "org.mockito:mockito-core:$mockito_version"
    const val mockitoAndroid = "org.mockito:mockito-android:$mockito_version"

    /**
     * Using Mockito with Kotlin
     * https://github.com/nhaarman/mockito-kotlin
     */
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:$mockitokotlin_version"

    /***
     * mocking library for Kotlin
     * http://mockk.io
     */
    const val mokk = "io.mockk:mockk:$mockk_version"

    /**
     * Strikt is an assertion library for Kotlin intended for use with a test runner such as JUnit or Spek.
     * https://strikt.io/
     */
    const val strikt = "io.strikt:strikt-core:$strikt_version"

}
