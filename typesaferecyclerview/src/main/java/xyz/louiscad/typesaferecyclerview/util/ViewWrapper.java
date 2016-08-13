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
