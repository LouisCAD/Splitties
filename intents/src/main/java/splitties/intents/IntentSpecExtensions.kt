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

import android.content.Context
import android.content.Intent
import splitties.bundle.BundleSpec
import splitties.init.appCtx

// Intent

inline fun <ISpec : IntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> ISpec.intent(
        configIntent: Intent.(intentSpec: ISpec, extrasSpec: ExtrasSpec) -> Unit
) = Intent(appCtx, klass).also { it.configIntent(this, extrasSpec) }

inline fun <ISpec : IntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> ISpec.intent(
        configIntent: Intent.(intentSpec: ISpec) -> Unit = {}
) = Intent(appCtx, klass).also { it.configIntent(this) }

// Activity

inline fun <ISpec: ActivityIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.start(
        intentSpec: ISpec,
        configIntent: Intent.(intentSpec: ISpec, extrasSpec: ExtrasSpec) -> Unit
) = startActivity(intentSpec.intent(configIntent))

inline fun <ISpec: ActivityIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.start(
        intentSpec: ISpec,
        configIntent: Intent.(intentSpec: ISpec) -> Unit = {}
) = startActivity(intentSpec.intent(configIntent))

// BroadcastReceiver

inline fun <ISpec: BroadcastReceiverIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.sendBroadcast(
        intentSpec: ISpec,
        configIntent: Intent.(intentSpec: ISpec, extrasSpec: ExtrasSpec) -> Unit
) = sendBroadcast(intentSpec.intent(configIntent))

inline fun <ISpec: BroadcastReceiverIntentSpec<*, ExtrasSpec>, ExtrasSpec : BundleSpec> Context.sendBroadcast(
        intentSpec: ISpec,
        configIntent: Intent.(intentSpec: ISpec) -> Unit = {}
) = sendBroadcast(intentSpec.intent(configIntent))
