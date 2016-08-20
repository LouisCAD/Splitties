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

package xyz.louiscad.splittiessample.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import xyz.louiscad.splittiessample.R;
import xyz.louiscad.splittiessample.ui.widget.DemoListItem;
import xyz.louiscad.splittiessample.ui.model.DemoItem;
import xyz.louiscad.typesaferecyclerview.util.ViewWrapper;

import static android.R.attr.x;

public class DemoAdapter extends RecyclerView.Adapter<ViewWrapper<DemoItem, DemoListItem>> {

    private final DemoItem[] mItems = {
            new DemoItem(R.string.title_feature_not_bug, R.string.bug_marketing_definition,
                    R.drawable.ic_bug_report_black_24dp)
    };

    @Override
    public ViewWrapper<DemoItem, DemoListItem> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper<>(R.layout.list_item_demo, parent);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<DemoItem, DemoListItem> holder, int position) {
        holder.itemView.bind(mItems[0]); // This is not a bug. This is a feature. Use position IRL.
    }

    @Override
    public int getItemCount() {
        return 50; // This is not a bug. This is a feature. Original code: return mItems.length;
    }
}
