/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
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
package splitties.bundle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import splitties.mainthread.isMainThread
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

inline fun <Spec : BundleSpec, R> Activity.withExtras(
    spec: Spec,
    crossinline block: Spec.() -> R
): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return intent.extras.with(spec, block)
}

inline fun <Spec : BundleSpec> Intent.putExtras(
    spec: Spec,
    crossinline block: Spec.() -> Unit
) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    replaceExtras((extras ?: Bundle()).apply { with(spec, block) })
}

inline fun <Spec : BundleSpec, R> Bundle.with(
    spec: Spec,
    crossinline block: Spec.() -> R
): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        this.putIn(spec)
        spec.block()
    } finally {
        removeBundleFrom(spec)
    }
}

@PublishedApi
internal fun Bundle.putIn(spec: BundleSpec) {
    if (isMainThread) spec.currentBundle = this else spec.bundleByThread.set(this)
}

@PublishedApi
internal fun removeBundleFrom(spec: BundleSpec) {
    if (isMainThread) spec.currentBundle = null else spec.bundleByThread.remove()
}
