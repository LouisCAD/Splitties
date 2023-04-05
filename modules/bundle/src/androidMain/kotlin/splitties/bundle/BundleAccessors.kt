/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.bundle

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * This function allows to read extras of an [Activity] in the passed [block] using a [BundleSpec].
 *
 * **WARNING:** This function doesn't allow writing to the passed [spec], because getting extras
 * from an [Activity] [Intent] returns a defensive copy, which doesn't take changes into account.
 *
 * _Any attempt to violate this will result in an [IllegalStateException] to be thrown._
 *
 * If you want to mutate the extras (i.e. setting values to properties defined in [spec]), use
 * [putExtras] instead.
 */
inline fun <Spec : BundleSpec, R> Activity.withExtras(
    spec: Spec,
    crossinline block: Spec.() -> R
): R {
    //TODO: Uncomment line below when https://youtrack.jetbrains.com/issue/KT-29510 is fixed.
    //contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return intent.withExtras(spec, block)
}

/**
 * This function allows to read extras of an [Intent] in the passed [block] using a [BundleSpec].
 *
 * **WARNING:** This function doesn't allow writing to the passed [spec], because getting extras
 * from an [Intent] returns a defensive copy, which doesn't take changes into account.
 *
 * _Any attempt to violate this will result in an [IllegalStateException] to be thrown._
 *
 * If you want to mutate the extras (i.e. setting values to properties defined in [spec]), use
 * [putExtras] instead.
 */
inline fun <Spec : BundleSpec, R> Intent.withExtras(
    spec: Spec,
    crossinline block: Spec.() -> R
): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        spec.isReadOnly = true
        spec.currentBundle = extras ?: Bundle()
        spec.block()
    } finally {
        spec.currentBundle = null
        spec.isReadOnly = false
    }
}

/**
 * This function allows to read **and write** extras of an [Activity] in the passed [block] using a
 * [BundleSpec]. _If you don't need to write to the extras, use [withExtras] instead._
 */
inline fun <Spec : BundleSpec> Activity.putExtras(
    spec: Spec,
    crossinline block: Spec.() -> Unit
) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    intent.putExtras(spec, block)
}

/**
 * This function allows to read **and write** extras of an [Intent] in the passed [block] using a
 * [BundleSpec]. _If you don't need to write to the extras, use [withExtras] instead._
 */
inline fun <Spec : BundleSpec> Intent.putExtras(
    spec: Spec,
    crossinline block: Spec.() -> Unit
) {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    replaceExtras((extras ?: Bundle()).apply { with(spec, block) })
}

/**
 * This function allows to read and write content of a [Bundle] in the passed [block] using a
 * [BundleSpec]. _If you are working on an [Activity] extras, use [withExtras] or [putExtras]
 * instead of this method._
 */
inline fun <Spec : BundleSpec, R> Bundle.with(
    spec: Spec,
    crossinline block: Spec.() -> R
): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        spec.currentBundle = this
        spec.block()
    } finally {
        spec.currentBundle = null
    }
}
