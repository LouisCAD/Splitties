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

package xyz.louiscad.splittiessample.ui.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import xyz.louiscad.selectableviewgroups.widget.SelectableRelativeLayout;
import xyz.louiscad.splittiessample.R;
import xyz.louiscad.splittiessample.annotations.See;
import xyz.louiscad.splittiessample.ui.model.DemoItem;
import xyz.louiscad.typesaferecyclerview.util.ViewWrapper;

/**
 * As you can see, this View is made to be used with {@link DemoItem} as a {@link RecyclerView}
 * list item.
 */
@EViewGroup
@See(layoutRes = R.layout.list_item_demo, layoutResUseOnly = true)
public class DemoListItem extends SelectableRelativeLayout implements ViewWrapper.Binder<DemoItem> {

    @ViewById
    ImageView iconImageView;

    @ViewById
    TextView titleTextView, detailTextView;

    public DemoListItem(Context context) {
        super(context);
    }

    public DemoListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DemoListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public DemoListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void bind(DemoItem demoItem) {
        iconImageView.setImageResource(demoItem.iconResId);
        titleTextView.setText(demoItem.titleResId);
        detailTextView.setText(demoItem.detailResId);
        setOnClickListener(demoItem);
    }

    @Override
    public void setViewHolder(ViewWrapper holder) {
        // We don't need the ViewHolder here, so no field to set.
    }
}
