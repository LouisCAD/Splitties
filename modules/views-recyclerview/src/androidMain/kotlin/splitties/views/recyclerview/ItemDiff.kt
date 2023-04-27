/*
 * Copyright 2023 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.views.recyclerview

import androidx.recyclerview.widget.DiffUtil

@JvmInline
value class ItemDiff<T> @PublishedApi internal constructor(
    @PublishedApi internal val diffUtilItemCallback: DiffUtil.ItemCallback<T>
) {

    @Suppress("nothing_to_inline")
    inline fun areItemsTheSame(old: T & Any, new: T & Any): Boolean {
        return diffUtilItemCallback.areItemsTheSame(old, new)
    }

    @Suppress("nothing_to_inline")
    inline fun areContentsTheSame(old: T & Any, new: T & Any): Boolean {
        return diffUtilItemCallback.areContentsTheSame(old, new)
    }

    @Suppress("nothing_to_inline")
    inline fun toItemCallback(): DiffUtil.ItemCallback<T> = diffUtilItemCallback

    companion object {
        fun <T> dataClassWithoutKey() = dataClassWithKey<T> { this }
        fun <T> dataClassWithKey(getKey: T.() -> Any?): ItemDiff<T> = ItemDiff(
             object : DiffUtil.ItemCallback<T>() {
                 override fun areItemsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
                    return oldItem.getKey() == newItem.getKey()
                 }

                 @Suppress("DiffUtilEquals")
                 override fun areContentsTheSame(oldItem: T & Any, newItem: T & Any): Boolean {
                    return oldItem == newItem
                 }

            }
        )
    }
}

@PublishedApi
@Suppress("nothing_to_inline")
internal inline fun <T> DiffUtil.ItemCallback<T>.wrap() = ItemDiff(this)

inline operator fun <reified A, reified B, R> ItemDiff<A>.plus(
    other: ItemDiff<B>
): ItemDiff<R> where A : R, B : R = compoundItemDiff(this, other)

@PublishedApi
internal inline fun <reified A, reified B, R> compoundItemDiff(
    diffA: ItemDiff<A>,
    diffB: ItemDiff<B>
): ItemDiff<R> where A : R, B : R = object : DiffUtil.ItemCallback<R>() {

    override fun areItemsTheSame(oldItem: R & Any, newItem: R & Any): Boolean = when (oldItem) {
        is A -> newItem is A && diffA.areItemsTheSame(oldItem, newItem)
        is B -> newItem is B && diffB.areItemsTheSame(oldItem, newItem)
        else -> error("Unexpected type")
    }

    override fun areContentsTheSame(oldItem: R & Any, newItem: R & Any) = when (oldItem) {
        is A -> newItem is A && diffA.areContentsTheSame(oldItem, newItem)
        is B -> newItem is B && diffB.areContentsTheSame(oldItem, newItem)
        else -> error("Unexpected type")
    }
}.wrap()
