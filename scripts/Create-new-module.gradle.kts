/*
* Copyright (c) 2016. Louis Cognault Ayeva Derman
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

//This is NOT a gradle script. It includes .gradle so the IDE recognizes it, but it should only use Kotlin stdlib.
println("Welcome in Create new module by Louis CAD")

inline fun <R> runOrRetry(block: () -> R): R {
    while (true) try {
        return block()
    } catch (t: Exception) {
        println(t.message)
    }
}

val currentDir = File(".")
val projectDir = currentDir.parentFile!!
val modulesDir = projectDir.resolve("modules")
val settingsFile = projectDir.resolve("settings.gradle.kts")
check(settingsFile.exists()) { "No ${settingsFile.name} found!" }
val templatesDirectory = currentDir.resolve("gradle_templates")
check(templatesDirectory.exists()) { "Template directory (${templatesDirectory.name}) not found!" }
val templateDirNames = templatesDirectory.list { file, name ->
    file.isDirectory && name.startsWith('.').not()
}.asList()
check(templateDirNames.isNotEmpty()) { "No templates found!" }
println("Please select a template")
templateDirNames.forEachIndexed { i, fileName ->
    println("${i + 1}. $fileName")
}
println("Enter the number: ")
val templateIndex = runOrRetry {
    val input = readLine() ?: ""
    val number = input.toIntOrNull() ?: throw IllegalStateException("Please, input a number.")
    number.also {
        require(it in 1..templateDirNames.size) { "$it is not among the available options." }
    } - 1
}
val selectedTemplateDirName = templateDirNames[templateIndex]!!
val selectedTemplateDir = templatesDirectory.resolve(selectedTemplateDirName)
println("You selected $selectedTemplateDirName\n")
val moduleName = runOrRetry {
    println("Please type the name of the module you want to create")
    println("Enter the name: ")
    (readLine() ?: "").also {
        require(it.isNotBlank()) { "Name entered is blank" }
        //TODO: Check name is valid
        val file = modulesDir.resolve(it)
        check(file.exists().not()) {
            "A ${if (file.isDirectory) "directory" else "file"} already has this name!"
        }
    }
}
val destinationDir = modulesDir.resolve(moduleName)
check(selectedTemplateDir.copyRecursively(destinationDir)) { "Copy operation didn't succeed" }

/**
 * Runs the [action] on this file, then, if the result of the lambda (if not null) or this file,
 * is a directory, calls this function on all the files inside.
 * If the directory is moved or renamed in [action], you need to return it in the lambda, or failure
 * will happen because the referenced file no longer exists.
 */
fun File.doRecursively(action: (File) -> File?) {
    val file = action(this) ?: this
    if (file.isDirectory) file.listFiles().forEach { it.doRecursively(action) }
}

class LazyMap<K, V>(
    private val backingMap: MutableMap<K, V> = mutableMapOf(),
    private val createEntry: (K) -> V
) : Map<K, V> by backingMap {
    override fun get(key: K): V {
        if (key !in backingMap) backingMap[key] = createEntry(key)
        return backingMap[key]!!
    }
}

val promptingMap = LazyMap<String, String>(mutableMapOf("module name" to moduleName)) { key ->
    runOrRetry {
        println("$key required. Please, enter it:")
        (readLine() ?: "").also {
            require(it.isNotBlank()) { "Blank fields are not supported yet." }
        }
    }
}

destinationDir.doRecursively { file ->
    val templatePrefix = "!template!"
    if (file.isDirectory) {
        if (file.name.startsWith(templatePrefix)) {
            val key = file.name.substringAfter(templatePrefix)
            val destination: File = file.resolveSibling(promptingMap[key].replace('.', '/'))
            destination.mkdirs()
            check(file.renameTo(destination)) {
                "Failed to rename $file to $destination"
            }
            return@doRecursively destination
        }
    } else {
        if (file.name == "!empty_dir_placeholder!") {
            file.delete()
            return@doRecursively null
        }
        if (file.extension == "file_template") {
            val newFile = file.resolveSibling(file.nameWithoutExtension)
            check(file.renameTo(newFile))
            val initialCode = newFile.readText()
            var code = initialCode
            val opening = "€{"
            val closing = '}'
            while (true) {
                val openingIndex = code.indexOf(opening)
                if (openingIndex == -1) break
                val closingIndex = code.indexOf(closing).also { check(it != -1) }
                val keyStartIndex = openingIndex + opening.length
                val key = code.substring(keyStartIndex, closingIndex)
                val toBeReplaced = code.substring(openingIndex, closingIndex + 1)
                val replacement = promptingMap[key]
                code = code.replace(toBeReplaced, replacement)
            }
            if (code != initialCode) newFile.writeText(code)
        }
    }
    null
}

//TODO("Update settings.gradle.kts file (maybe should prompt the user)")
//Idea: Prompt copying the module name (with double quotes and column?) to clipboard.
//Idea: Support loop prompt for api and implementation dependencies with set of existing values in number choices.
//Idea: Support optional directories and files.
