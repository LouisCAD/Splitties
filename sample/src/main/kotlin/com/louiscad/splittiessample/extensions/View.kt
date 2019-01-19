package com.louiscad.splittiessample.extensions

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

@UseExperimental(ExperimentalContracts::class)
inline fun <R> View.visibleInScope(finallyInvisible: Boolean = false, block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        isVisible = true
        block()
    } finally {
        visibility = if (finallyInvisible) View.INVISIBLE else View.GONE
    }
}

@UseExperimental(ExperimentalContracts::class)
inline fun <R> View.goneInScope(block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        isVisible = false
        block()
    } finally {
        isVisible = true
    }
}

@UseExperimental(ExperimentalContracts::class)
inline fun <R> View.invisibleInScope(block: () -> R): R {
    contract { callsInPlace(block, InvocationKind.EXACTLY_ONCE) }
    return try {
        isInvisible = true
        block()
    } finally {
        isInvisible = false
    }
}
