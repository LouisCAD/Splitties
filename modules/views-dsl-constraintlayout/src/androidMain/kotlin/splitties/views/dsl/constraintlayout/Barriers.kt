/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package splitties.views.dsl.constraintlayout

import android.view.View
import androidx.constraintlayout.widget.Barrier
import splitties.views.dsl.core.add
import splitties.views.dsl.core.view
import splitties.views.dsl.core.wrapContent
import splitties.views.existingOrNewId
import androidx.constraintlayout.widget.ConstraintLayout as CL

inline fun CL.startBarrier(vararg views: View): Barrier = barrier(BarrierType.START, views)
inline fun CL.leftBarrier(vararg views: View): Barrier = barrier(BarrierType.LEFT, views)
inline fun CL.topBarrier(vararg views: View): Barrier = barrier(BarrierType.TOP,views)
inline fun CL.endBarrier(vararg views: View): Barrier = barrier(BarrierType.END,views)
inline fun CL.rightBarrier(vararg views: View): Barrier = barrier(BarrierType.RIGHT, views)
inline fun CL.bottomBarrier(vararg views: View): Barrier = barrier(BarrierType.BOTTOM, views)

fun CL.barrier(type: BarrierType, views: Array<out View>): Barrier = add(view(::Barrier) {
    referencedIds = IntArray(views.size) { views[it].existingOrNewId }
    this.type = type.intValue
}, lParams(wrapContent, wrapContent))

fun CL.barrier(type: BarrierType, views: List<View>): Barrier = add(view(::Barrier) {
    referencedIds = IntArray(views.size) { views[it].existingOrNewId }
    this.type = type.intValue
}, lParams(wrapContent, wrapContent))

@Suppress("NON_PUBLIC_PRIMARY_CONSTRUCTOR_OF_INLINE_CLASS")
inline class BarrierType @PublishedApi internal constructor(internal val intValue: Int) {
    companion object {
        inline val START get() = BarrierType(Barrier.START)
        inline val LEFT get() = BarrierType(Barrier.LEFT)
        inline val TOP get() = BarrierType(Barrier.TOP)
        inline val END get() = BarrierType(Barrier.END)
        inline val RIGHT get() = BarrierType(Barrier.RIGHT)
        inline val BOTTOM get() = BarrierType(Barrier.BOTTOM)
    }
}
