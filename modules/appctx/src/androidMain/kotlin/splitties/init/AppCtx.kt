/*
 * Copyright 2019-2021 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.init

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Service
import android.app.backup.BackupAgent
import android.content.Context
import android.content.ContextWrapper
import android.os.Build.VERSION.SDK_INT
import android.view.ContextThemeWrapper

/**
 * **WARNING!** Please, do not use this context if you rely on a scoped [Context] such as accessing
 * themed resources from an [Activity] or a [ContextThemeWrapper].
 *
 * This [Context] which by default is an instance of your [Application] class is initialized before
 * [Application.onCreate] is called (but after your [Application]'s constructor has run) thanks to
 * AndroidX App Startup which is initialized by Android as early as possible when your app's
 * process is created.
 */
val appCtx: Context get() = internalCtx ?: internalCtxUninitialized()

private fun internalCtxUninitialized(): Nothing {
    val processName = getProcessName()
    val isDefaultProcess = ':' !in processName
    val (cause: String, solutions: List<String>) = when {
        isDefaultProcess -> "App Startup didn't run" to listOf(
            "If App Startup has been disabled, enable it back in the AndroidManifest.xml file of the app.",
            "For other cases, call injectAsAppCtx() in the app's Application subclass in its initializer or in its onCreate function."
        )
        else -> "App Startup is not enabled for non default processes" to listOf(
            "Call injectAsAppCtx() in the app's Application subclass in its initializer or in its onCreate function."
        )
    }
    error(buildString {
        appendLine("appCtx has not been initialized!")
        when (solutions.size) {
            1 -> appendLine("Possible solution: ${solutions.single()}")
            else -> {
                appendLine("$cause. Possible solutions:")
                solutions.forEachIndexed { index, solution ->
                    append(index + 1); append(". "); append(solution)
                }
            }
        }
    })
}

@SuppressLint("StaticFieldLeak")
private var internalCtx: Context? = null

/**
 * **Usage of this method is discouraged** because the [appCtx] should be automatically initialized
 * and setting it to a custom Context may cause unpredictable behavior in some parts of the app,
 * especially if some libraries rely on the true applicationContext.
 *
 * **However**, if your app needs to use [appCtx] outside of the default process and the reflection
 * method throws an exception on some devices, you may find useful to call this method from your
 * [Application.onCreate]. This method may also be useful if you need change the [appCtx] value with
 * one from a configuration context with an overriden locale for example.
 *
 * **If you ever use this method because of non-default process, and throwing reflection init,
 * be sure to call this method before you try to access [appCtx] or [directBootCtx].**
 *
 * Note that the input [Context] is checked for memory leak ability, and will throw if you try to
 * pass an [Activity], a [Service], a [BackupAgent], a [Context] from another app or
 * a [ContextWrapper] based on these classes.
 *
 * @see appCtx
 * @see canLeakMemory
 * @see Context.createConfigurationContext
 */
fun Context.injectAsAppCtx() {
    require(!canLeakMemory()) { "The passed Context($this) would leak memory!" }
    internalCtx = this
}

/**
 * This method will return true on [Context] implementations known to be able to leak memory.
 * This includes [Activity], [Service], the lesser used and lesser known [BackupAgent], as well as
 * any [ContextWrapper] having one of these as its base context.
 */
fun Context.canLeakMemory(): Boolean = when (this) {
    is Application -> false
    is Activity, is Service, is BackupAgent -> true
    is ContextWrapper -> if (baseContext === this) true else baseContext.canLeakMemory()
    else -> applicationContext === null
}

private fun getProcessName(): String {
    if (SDK_INT >= 28) return Application.getProcessName()
    @SuppressLint("PrivateApi", "DiscouragedPrivateApi")
    val activityThread = Class.forName("android.app.ActivityThread")
    // Before API 18, the method was incorrectly named "currentPackageName",
    // but it still returned the process name
    // See https://github.com/aosp-mirror/platform_frameworks_base/commit/b57a50bd16ce25db441da5c1b63d48721bb90687
    val methodName = if (SDK_INT >= 18) "currentProcessName" else "currentPackageName"
    return activityThread.getDeclaredMethod(methodName).invoke(null) as String
}
