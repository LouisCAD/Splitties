/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */
package splitties.collections

/**
 * Iterates the receiver [List] using an index instead of an [Iterator] like [forEach] would do.
 * Using this function saves an [Iterator] allocation, which is good for immutable lists or usages
 * confined to a single thread like UI thread only use.
 * However, this method will not detect concurrent modification, except if the size of the list
 * changes on an iteration as a result, which may lead to unpredictable behavior.
 *
 * @param action the action to invoke on each list element.
 */
inline fun <T> List<T>.forEachByIndex(action: (T) -> Unit) {
    val initialSize = size
    for (i in 0..lastIndex) {
        if (size != initialSize) throw ConcurrentModificationException()
        action(get(i))
    }
}

/**
 * Iterates the receiver [List] using an index instead of an [Iterator] like [forEachIndexed] would
 * do. Using this function saves an [Iterator] allocation, which is good for immutable lists or
 * usages confined to a single thread like UI thread only use.
 * However, this method will not detect concurrent modification, except if the size of the list
 * changes on an iteration as a result, which may lead to unpredictable behavior.
 *
 * @param action the action to invoke on each list element.
 */
inline fun <T> List<T>.forEachWithIndex(action: (Int, T) -> Unit) {
    val initialSize = size
    for (i in 0..lastIndex) {
        if (size != initialSize) throw ConcurrentModificationException()
        action(i, get(i))
    }
}

/**
 * Iterates the receiver [List] using an index instead of an [Iterator] like [forEach] would do, but
 * in reverse order (from last index down to zero).
 * Using this function saves an [Iterator] allocation, which is good for immutable lists or usages
 * confined to a single thread like UI thread only use.
 * However, this method will not detect concurrent modification, except if the size of the list
 * changes on an iteration as a result, which may lead to unpredictable behavior.
 *
 * @param action the action to invoke on each list element.
 */
inline fun <T> List<T>.forEachReversedByIndex(action: (T) -> Unit) {
    val initialSize = size
    for (i in lastIndex downTo 0) {
        if (size != initialSize) throw ConcurrentModificationException()
        action(get(i))
    }
}

/**
 * Iterates the receiver [List] using an index instead of an [Iterator] like [forEachIndexed] would
 * do, but in reverse order (from last index down to zero). Using this function saves an [Iterator]
 * allocation, which is good for immutable lists or usages confined to a single thread like
 * UI thread only use. However, this method will not detect concurrent modification, except if the
 * size of the list changes on an iteration as a result, which may lead to unpredictable behavior.
 *
 * @param action the action to invoke on each list element.
 */
inline fun <T> List<T>.forEachReversedWithIndex(action: (Int, T) -> Unit) {
    val initialSize = size
    for (i in lastIndex downTo 0) {
        if (size != initialSize) throw ConcurrentModificationException()
        action(i, get(i))
    }
}

/**
 * Iterates the receiver [List] using an index instead of an [Iterator] like [forEachIndexed] would
 * do, but in reverse order (from last index down to zero). Using this function saves an [Iterator]
 * allocation, which is good for immutable lists or usages confined to a single thread like
 * UI thread only use. However, this method will not detect all concurrent modification.
 *
 * If [allowSafeModifications] is true, you can mutate the list while iterating as long as iterating
 * down is still possible (otherwise a [ConcurrentModificationException] is thrown).
 *
 * @param action the action to invoke on each list element.
 */
inline fun <T> List<T>.forEachReversedWithIndex(
    allowSafeModifications: Boolean = false,
    action: (Int, T) -> Unit
) {
    val initialSize = size
    for (i in lastIndex downTo 0) {
        when {
            allowSafeModifications && i > lastIndex -> {
                throw ConcurrentModificationException()
            }
            allowSafeModifications.not() && size != initialSize -> {
                throw ConcurrentModificationException()
            }
        }
        action(i, get(i))
    }
}

/**
 * Execute an [action] to the first item witch matches the [predicate].
 * Do nothing if there's not match
 */
fun <T> Collection<T>.executeOnFirstMatch(predicate:(T) -> Boolean,action: (T) -> Unit) {
    val matched = this.firstOrNull(predicate)
    if (matched != null) {
        action(matched)
    }
}

/**
 * Toggle an [item] from a MutableList.
 */
fun <T> MutableList<T>.toggle(item: T): List<T> {
    if (this.contains(item)) {
        this.remove(item)
    } else {
        this.add(item)
    }
    return this
}
