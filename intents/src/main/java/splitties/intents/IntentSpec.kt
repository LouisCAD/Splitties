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
package splitties.intents

import android.app.Activity
import android.app.Service
import splitties.bundle.BundleSpec
import splitties.exceptions.unsupported
import android.content.BroadcastReceiver as BR

/**
 * @see ActivityIntentSpec
 * @see BroadcastReceiverIntentSpec
 * @see ServiceIntentSpec
 */
interface IntentSpec<T, out ExtrasSpec : BundleSpec> {
    val klass: Class<T>
    val extrasSpec: ExtrasSpec
}

interface ActivityIntentSpec<T : Activity, out ExtrasSpec : BundleSpec> : IntentSpec<T, ExtrasSpec>
interface BroadcastReceiverIntentSpec<T : BR, out ExtrasSpec : BundleSpec> :
    IntentSpec<T, ExtrasSpec>

interface ServiceIntentSpec<T : Service, out ExtrasSpec : BundleSpec> : IntentSpec<T, ExtrasSpec>

// Activity Intent

inline fun <reified T : Activity, ExtrasSpec : BundleSpec> activitySpec(
    extrasSpec: ExtrasSpec
) = object : ActivityIntentSpec<T, ExtrasSpec> {
    override val klass = T::class.java
    override val extrasSpec = extrasSpec
}

inline fun <reified T : Activity> activityWithoutExtrasSpec(): ActivityIntentSpec<T, Nothing> {
    return object : ActivityIntentSpec<T, Nothing> {
        override val klass = T::class.java
        override val extrasSpec get() = unsupported()
    }
}

// BroadcastReceiver Intent

inline fun <reified T : BR, ExtrasSpec : BundleSpec> receiverSpec(
    extrasSpec: ExtrasSpec
) = object : BroadcastReceiverIntentSpec<T, ExtrasSpec> {
    override val klass = T::class.java
    override val extrasSpec = extrasSpec
}

inline fun <reified T : BR> receiverWithoutExtrasSpec(): BroadcastReceiverIntentSpec<T, Nothing> {
    return object : BroadcastReceiverIntentSpec<T, Nothing> {
        override val klass = T::class.java
        override val extrasSpec get() = unsupported()
    }
}

// Service Intent

inline fun <reified T : Service, ExtrasSpec : BundleSpec> serviceSpec(
    extrasSpec: ExtrasSpec
) = object : ServiceIntentSpec<T, ExtrasSpec> {
    override val klass = T::class.java
    override val extrasSpec = extrasSpec
}

inline fun <reified T : Service> serviceWithoutExtrasSpec(): ServiceIntentSpec<T, Nothing> {
    return object : ServiceIntentSpec<T, Nothing> {
        override val klass = T::class.java
        override val extrasSpec get() = unsupported()
    }
}
