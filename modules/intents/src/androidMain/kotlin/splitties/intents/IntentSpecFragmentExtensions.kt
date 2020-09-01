/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.intents

import android.content.Intent
import androidx.fragment.app.Fragment
import splitties.bundle.BundleSpec
import splitties.exceptions.unsupported

// WARNING. If this file is renamed, edit the proguard-rules.pro file accordingly.

@JvmName("new")
@Suppress("DeprecatedCallableAddReplaceWith", "unused", "UNUSED_PARAMETER")
@Deprecated(noExtrasSpecMsg, level = DeprecationLevel.ERROR) // FOOL GUARD, DO NOT REMOVE
inline fun <ISpec : ActivityIntentSpec<*, Nothing>> Fragment.start(
    intentSpec: ISpec,
    configIntent: Intent.(intentSpec: ISpec, extrasSpec: Nothing) -> Unit
): Nothing = unsupported()

inline fun <ISpec : ActivityIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Fragment.start(
    intentSpec: ISpec,
    crossinline configIntent: Intent.(intentSpec: ISpec, extrasSpec: ExtrasSpec) -> Unit
) = startActivity(intentSpec.intent(configIntent))

inline fun <ISpec : ActivityIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Fragment.start(
    intentSpec: ISpec,
    configIntent: Intent.(intentSpec: ISpec) -> Unit = {}
) = startActivity(intentSpec.intent(configIntent))
