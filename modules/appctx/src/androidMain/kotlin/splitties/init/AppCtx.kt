/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.init

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Service
import android.app.backup.BackupAgent
import android.content.ContentProvider
import android.content.Context
import android.content.ContextWrapper
import android.view.ContextThemeWrapper

/**
 * **WARNING!** Please, do not use this context if you rely on a scoped [Context] such as accessing
 * themed resources from an [Activity] or a [ContextThemeWrapper].
 *
 * This [Context] which by default is an instance of your [Application] class is initialized before
 * [Application.onCreate] is called (but after your [Application]'s constructor has run) thanks to
 * [AppCtxInitProvider] which is initialized by Android as early as possible when your app's
 * process is created.
 */
val appCtx: Context get() = internalCtx ?: initAndGetAppCtxWithReflection()

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

/**
 * This methods is only run if [appCtx] is accessed while [AppCtxInitProvider] hasn't been
 * initialized. This may happen in case you're accessing it outside the default process, or in case
 * you are accessing it in a [ContentProvider] with a higher priority than [AppCtxInitProvider]
 * (900 at the time of writing this doc).
 *
 * If you don't want this code that uses reflection to ever run, see [injectAsAppCtx].
 */
@SuppressLint("PrivateApi")
private fun initAndGetAppCtxWithReflection(): Context {
    // Fallback, should only run once per non default process.
    val activityThread = Class.forName("android.app.ActivityThread")
    val ctx = activityThread.getDeclaredMethod("currentApplication").invoke(null) as Context
    internalCtx = ctx
    return ctx
}
