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

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.N
import android.support.annotation.RequiresApi

/**
 * Lazily creates a device protected storage Context on Android N+ devices,
 * or initializes itself to [appCtx] if the device runs Android M or an older version.
 * See [Direct Boot documentation](https://developer.android.com/training/articles/direct-boot.html)
 * to learn more.
 */
inline val directBootCtx: Context get() = if (SDK_INT < N) appCtx else deviceProtectedStorageCtx.value

@PublishedApi
@RequiresApi(N)
internal val deviceProtectedStorageCtx = lazy { appCtx.createDeviceProtectedStorageContext() }
