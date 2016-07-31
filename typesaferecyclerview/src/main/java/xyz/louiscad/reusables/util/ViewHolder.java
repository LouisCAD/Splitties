package xyz.louiscad.reusables.util;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Type-safe version of {@link RecyclerView.ViewHolder}.
 */
public class ViewHolder<V extends View> extends RecyclerView.ViewHolder {

    /**
     * You can access this typed field instead of casting {@link RecyclerView.ViewHolder#itemView}.
     */
    public final V itemView;

    /**
     * Creates ViewHolder from the provided View.
     * @see #ViewHolder(int, ViewGroup)
     */
    public ViewHolder(V itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    /**
     * Creates a ViewHolder from a layout resource id.
     *
     * @param layoutResId ID for an XML layout resource to load (e.g., R.layout.main_page)
     * @param parent      pass the first argument received in
     *                    {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    @SuppressWarnings("unchecked cast")
    public ViewHolder(@LayoutRes int layoutResId, ViewGroup parent) {
        this((V) inflate(layoutResId, parent));
    }

    @SuppressWarnings("unchecked cast")
    private static <V extends View> V inflate(@LayoutRes int layoutResId, ViewGroup parent) {
        return (V) LayoutInflater.from(parent.getContext())
                .inflate(layoutResId, parent, false);
    }
}
