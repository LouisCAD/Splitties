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

import android.app.Application
import android.content.Context
import android.os.Build

/**
 * **WARNING!** Please, do not use this context if you rely on a scoped [Context] such as accessing
 * themed resources from an Activity.
 *
 * This [Context] which is an instance of your [Application] class is initialized before
 * [Application.onCreate] is called (but after your [Application]'s constructor has run) thanks to
 * [AppCtxInitProvider] which is initialized by Android as early as possible when your app's
 * process is created.
 */
val appCtx: Context
    get() = internalCtx!!

/**
 * See [Direct Boot documentation](https://developer.android.com/training/articles/direct-boot.html)
 */
val directBootCtx: Context by lazy {
    if (Build.VERSION.SDK_INT < 24) appCtx else appCtx.createDeviceProtectedStorageContext()
}

/**
 * Call this method from your [Application] subclass if you need to use [appCtx] outside the default
 * process.
 */
fun setAppCtx(app: Application) {
    internalCtx = app
}

internal var internalCtx: Context? = null
