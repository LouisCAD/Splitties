package com.louiscad.splittiessample.extensions

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

inline fun <R> View.visibleInScope(finallyInvisible: Boolean = false, block: () -> R) = try {
    isVisible = true
    block()
} finally {
    visibility = if (finallyInvisible) View.INVISIBLE else View.GONE
}

inline fun <R> View.goneInScope(block: () -> R) = try {
    isVisible = false
    block()
} finally {
    isVisible = true
}

inline fun <R> View.invisibleInScope(block: () -> R) = try {
    isInvisible = true
    block()
} finally {
    isInvisible = false
}
