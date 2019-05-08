/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.typesaferecyclerview;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import splitties.views.LayoutInflaterKt;

/**
 * Type-safe version of {@link RecyclerView.ViewHolder}.
 */
public class ViewHolder<V extends View> extends RecyclerView.ViewHolder {

    /**
     * You can access this typed field instead of casting {@link RecyclerView.ViewHolder#itemView}.
     */
    public final @NonNull V itemView;

    /**
     * Creates ViewHolder from the provided View.
     * @see #ViewHolder(int, ViewGroup)
     */
    public ViewHolder(@NonNull V itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    /**
     * Creates a ViewHolder from a layout resource id.
     * @param layoutResId ID for an XML layout resource to load (e.g., R.layout.main_page)
     * @param parent      pass the first argument received in
     *                    {@link RecyclerView.Adapter#onCreateViewHolder(ViewGroup, int)}
     */
    public ViewHolder(@LayoutRes int layoutResId, @NonNull ViewGroup parent) {
        this(LayoutInflaterKt.<V>inflate(parent, layoutResId, false));
    }
}
