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
import android.support.annotation.CallSuper
import splitties.initprovider.InitProvider

/**
 * Initializes [appCtx] so it can be used where any [Context] can be used.
 *
 * If you use [appCtx] in another process than the default one. This will not be invoked.
 * The library will attempt to initialize [appCtx] using reflection instead. If you want to avoid
 * this, you can declare a subclass of this class in your manifest with the name of the process
 * properly specified to get it Automatically initialized. Alternatively, you can in your properly
 * declared custom [Application] call [injectAsAppCtx] passing `this` reference from the constructor
 * or the [Application.onCreate] method.
 */
open class AppCtxInitProvider : InitProvider() {
    @CallSuper override fun onCreate() = true.also { _ -> context.injectAsAppCtx() }
}
