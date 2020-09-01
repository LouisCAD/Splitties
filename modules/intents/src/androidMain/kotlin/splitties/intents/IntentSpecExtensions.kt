/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.intents

import android.content.Context
import android.content.Intent
import splitties.bundle.BundleSpec
import splitties.bundle.putExtras
import splitties.exceptions.unsupported
import splitties.init.appCtx

// Intent
@JvmName("new")
@Suppress("DeprecatedCallableAddReplaceWith", "unused", "UNUSED_PARAMETER")
@Deprecated(noExtrasSpecMsg, level = DeprecationLevel.ERROR) // FOOL GUARD, DO NOT REMOVE
inline fun <ISpec : IntentSpec<*, Nothing>> ISpec.intent(
    configIntent: Intent.(intentSpec: ISpec, extrasSpec: Nothing) -> Unit
): Nothing = unsupported()

inline fun <ISpec : IntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> ISpec.intent(
    crossinline configIntent: Intent.(intentSpec: ISpec, extrasSpec: ExtrasSpec) -> Unit
) = Intent(appCtx, klass).also { intent ->
    intent.putExtras(extrasSpec) { intent.configIntent(this@intent, extrasSpec) }
}

inline fun <ISpec : IntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> ISpec.intent(
    configIntent: Intent.(intentSpec: ISpec) -> Unit = {}
) = Intent(appCtx, klass).also { it.configIntent(this) }

// Activity

@JvmName("new")
@Suppress("DeprecatedCallableAddReplaceWith", "unused", "UNUSED_PARAMETER")
@Deprecated(noExtrasSpecMsg, level = DeprecationLevel.ERROR) // FOOL GUARD, DO NOT REMOVE
inline fun <ISpec : ActivityIntentSpec<*, Nothing>> Context.start(
    intentSpec: ISpec,
    configIntent: Intent.(intentSpec: ISpec, extrasSpec: Nothing) -> Unit
): Nothing = unsupported()

inline fun <ISpec : ActivityIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.start(
    intentSpec: ISpec,
    crossinline configIntent: Intent.(intentSpec: ISpec, extrasSpec: ExtrasSpec) -> Unit
) = startActivity(intentSpec.intent(configIntent))

inline fun <ISpec : ActivityIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.start(
    intentSpec: ISpec,
    configIntent: Intent.(intentSpec: ISpec) -> Unit = {}
) = startActivity(intentSpec.intent(configIntent))

// BroadcastReceiver

@JvmName("new")
@Suppress("DeprecatedCallableAddReplaceWith", "unused", "UNUSED_PARAMETER")
@Deprecated(noExtrasSpecMsg, level = DeprecationLevel.ERROR) // FOOL GUARD, DO NOT REMOVE
inline fun <ISpec : BroadcastReceiverIntentSpec<*, Nothing>> Context.sendBroadcast(
    intentSpec: ISpec,
    configIntent: Intent.(intentSpec: ISpec, extrasSpec: Nothing) -> Unit
): Nothing = unsupported()

inline fun <ISpec : BroadcastReceiverIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.sendBroadcast(
    intentSpec: ISpec,
    crossinline configIntent: Intent.(intentSpec: ISpec, extrasSpec: ExtrasSpec) -> Unit
) = sendBroadcast(intentSpec.intent(configIntent))

inline fun <ISpec : BroadcastReceiverIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.sendBroadcast(
    intentSpec: ISpec,
    configIntent: Intent.(intentSpec: ISpec) -> Unit = {}
) = sendBroadcast(intentSpec.intent(configIntent))

internal const val noExtrasSpecMsg = "This IntentSpec has no ExtrasSpec. Use single arg lambda."
