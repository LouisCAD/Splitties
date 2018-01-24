/*
 * Copyright (c) 2017. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.typesaferecyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

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
    @SuppressWarnings("unchecked")
    public ViewHolder(@LayoutRes int layoutResId, @NonNull ViewGroup parent) {
        this(LayoutInflaterKt.<V>inflate(parent, layoutResId, false));
    }
}
