/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
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

package splitties.init

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * **WARNING!** Please, do not use this context if you rely on a scoped [Context] such as accessing
 * themed resources from an Activity.
 *
 * This [Context] which is an instance of your [Application] class is initialized before
 * [Application.onCreate] is called (but after your [Application]'s constructor has run) thanks to
 * [AppCtxInitProvider] which is initialized by Android as early as possible when your app's
 * process is created.
 */
inline val appCtx: Context get() = internalCtx ?: initAppCtxWithReflection()

@PublishedApi
@SuppressLint("StaticFieldLeak")
internal var internalCtx: Context? = null

@PublishedApi
@SuppressLint("PrivateApi")
internal fun initAppCtxWithReflection(): Context {
    // Fallback, should only run once per non default process.
    val activityThread = Class.forName("android.app.ActivityThread")
    val ctx = activityThread.getDeclaredMethod("currentApplication").invoke(null) as Context
    internalCtx = ctx
    return ctx
}

/**
 * Call this method from your [Application] subclass if you need to use [appCtx] outside the default
 * process and the reflection method throws an exception on some devices.
 *
 * If you ever use this method because of what is mentioned above, be sure to call this method
 * before you try to access [appCtx] or [directBootCtx].
 *
 * @see appCtx
 */
@Deprecated("Should not be needed.", ReplaceWith(""), DeprecationLevel.WARNING)
fun Application.injectAsAppCtx() {
    internalCtx = this
}
