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
package splitties.fragments

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentTransaction

inline fun FragmentActivity.fragmentTransaction(
    now: Boolean = true,
    allowStateLoss: Boolean = false,
    transactionBody: FragmentTransaction.() -> Unit
): Unit = supportFragmentManager.beginTransaction().apply(transactionBody).let { ft ->
    when {
        allowStateLoss -> if (now) ft.commitNowAllowingStateLoss() else ft.commitAllowingStateLoss()
        else -> if (now) ft.commitNow() else ft.commit()
    }
}

@Suppress("NOTHING_TO_INLINE")
inline fun FragmentTransaction.addToBackStack(): FragmentTransaction = addToBackStack(null)

inline fun <reified A : Activity> Fragment.start(configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(activity, A::class.java).apply(configIntent))
}

inline fun Fragment.startActivity(action: String, configIntent: Intent.() -> Unit = {}) {
    startActivity(Intent(action).apply(configIntent))
}
