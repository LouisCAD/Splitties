/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.fragments

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

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
