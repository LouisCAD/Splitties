package xyz.louiscad.reusables.util;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

/**
 * Use this class if your {@link ViewHolder} are always bound to the same data type.
 */
public class ViewWrapper<Data, V extends View & ViewWrapper.Binder<Data>> extends ViewHolder<V> {

    public ViewWrapper(V itemView) {
        super(itemView);
    }

    public ViewWrapper(@LayoutRes int layoutResId, ViewGroup parent) {
        super(layoutResId, parent);
    }

    public interface Binder<Data> {
        void bind(Data data);
    }
}
