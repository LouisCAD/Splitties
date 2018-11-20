package com.louiscad.splittiessample.extensions

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible

inline fun View.visibleInScope(finallyInvisible: Boolean = false, block: () -> Unit) = try {
    isVisible = true
    block()
} finally {
    visibility = if (finallyInvisible) View.INVISIBLE else View.GONE
}

inline fun View.goneInScope(block: () -> Unit) = try {
    isVisible = false
    block()
} finally {
    isVisible = true
}

inline fun View.invisibleInScope(block: () -> Unit) = try {
    isInvisible = true
    block()
} finally {
    isInvisible = false
}
