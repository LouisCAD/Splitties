@file:Suppress("PackageDirectoryMismatch")

package androidx.recyclerview.widget

import android.view.View
import splitties.experimental.InternalSplittiesApi

@InternalSplittiesApi
abstract class BindAwareViewHolder<E, V : View>(
    @JvmField val view: V
) : RecyclerView.ViewHolder(view) {

    @Deprecated(
        message = "Use view instead",
        replaceWith = ReplaceWith("view"),
        level = DeprecationLevel.ERROR
    )
    inline val itemViewWithType get() = view

    protected open fun onBind() = Unit

    protected open fun onUnbind() = Unit

    internal final override fun setFlags(flags: Int, mask: Int) {
        val wasBound = isBound
        super.setFlags(flags, mask)
        notifyBinding(wasBound, isBound)
    }

    internal final override fun addFlags(flags: Int) {
        val wasBound = isBound
        super.addFlags(flags)
        notifyBinding(wasBound, isBound)
    }

    internal final override fun clearPayload() {
        val wasBound = isBound
        super.clearPayload()
        notifyBinding(wasBound, isBound)
    }

    internal final override fun resetInternal() {
        val wasBound = isBound
        super.resetInternal()
        notifyBinding(wasBound, isBound)
    }

    private fun notifyBinding(previousBound: Boolean, currentBound: Boolean) {
        if (previousBound && !currentBound) {
            onUnbind()
        } else if (!previousBound && currentBound) {
            onBind()
        }
    }
}
