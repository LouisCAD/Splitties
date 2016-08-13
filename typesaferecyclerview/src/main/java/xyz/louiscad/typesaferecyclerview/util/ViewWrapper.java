package xyz.louiscad.typesaferecyclerview.util;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * Use this class if your {@link ViewHolder} are always bound to the same data type.
 */
public class ViewWrapper<Data, V extends View & ViewWrapper.Binder<Data>> extends ViewHolder<V> {

    /**
     * @param itemView the bind-able View to wrap.
     * @see ViewHolder#ViewHolder(View)
     */
    public ViewWrapper(V itemView) {
        super(itemView);
    }

    /**
     * The passed layoutResId <b>root View must implement {@link ViewWrapper.Binder<Data>}.</b>
     * @see ViewHolder#ViewHolder(int, ViewGroup)
     */
    public ViewWrapper(@LayoutRes int layoutResId, ViewGroup parent) {
        super(layoutResId, parent);
    }

    /**
     * Implement this interface in your custom {@link View} so it can be used with
     * {@link ViewWrapper} and be easily bound to data.
     *
     * @param <Data> the type of the data to be bound.
     */
    public interface Binder<Data> {
        void bind(Data data);
    }
}
