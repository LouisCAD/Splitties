/*
 * Copyright (c) 2016. Louis Cognault Ayeva Derman
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

package xyz.louiscad.typesaferecyclerview.util;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Use this class if your {@link ViewHolder} are always bound to the same data type.
 * @param <Data> The data you want to bind to your item view.
 * @param <V>    The type of your item views.
 * @param <Host> The host of your items views. It's recommended to be an interface defined in your
 *               item view (which can be called Host too) that your Activity, Fragment, Presenter
 *               or anything else you want implements. If you don't need this (i.e. read only list),
 *               you can use {@link Void} here.
 */
public class ViewWrapper<Data, V extends View & ViewWrapper.Binder<Data, Host>, Host>
        extends ViewHolder<V> {

    /**
     * @param itemView the bind-able View to wrap.
     * @param host     Shouldn't be null unless it's from {@link Void} type.
     * @see ViewHolder#ViewHolder(View)
     */
    public ViewWrapper(Host host, V itemView) {
        super(itemView);
        itemView.setHost(host);
        itemView.setViewHolder(this);
    }

    /**
     * @param layoutResId <b>It's root View must implement {@link ViewWrapper.Binder}.</b>
     * @param host        Shouldn't be null unless it's from {@link Void} type.
     * @see ViewHolder#ViewHolder(int, ViewGroup)
     */
    public ViewWrapper(Host host, @LayoutRes int layoutResId, ViewGroup parent) {
        super(layoutResId, parent);
        itemView.setHost(host);
        itemView.setViewHolder(this);
    }

    /**
     * Implement this interface in your custom {@link View} so it can be used with
     * {@link ViewWrapper} and be easily bound to data.
     * @param <Data> the type of the data to be bound.
     */
    public interface Binder<Data, Host> {
        /**
         * Is called during {@link ViewWrapper} creation (so only once). If you need to get the host
         * so you can talk to it from the item view (e.g. to handle a click), save it to a field to
         * interact with it later.
         * @param host Shouldn't be null unless it's from {@link Void} type.
         */
        void setHost(Host host);
        /**
         * Is called during {@link ViewWrapper} creation (so only once). If you need to get
         * the position from the item view, save the holder passed parameter to a field
         * so you can call {@link RecyclerView.ViewHolder#getAdapterPosition()} later.
         * @param holder The {@link RecyclerView.ViewHolder} holding this View
         */
        void setViewHolder(ViewWrapper holder);
        void bind(Data data);
    }
}
