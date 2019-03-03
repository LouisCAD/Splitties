/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
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
