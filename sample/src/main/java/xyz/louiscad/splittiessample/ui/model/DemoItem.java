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

package xyz.louiscad.splittiessample.ui.model;

import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import xyz.louiscad.splittiessample.R;
import xyz.louiscad.splittiessample.ui.activity.DemoActivity;

import static android.support.design.widget.Snackbar.LENGTH_INDEFINITE;
import static java.lang.Integer.MAX_VALUE;

/**
 * @see xyz.louiscad.splittiessample.ui.widget.DemoListItem
 */
public class DemoItem extends ImmutableBasicItem implements View.OnClickListener {

    public DemoItem(@StringRes int titleResId, @StringRes int detailResId, @DrawableRes int iconResId) {
        super(titleResId, detailResId, iconResId);
    }

    @Override
    public void onClick(View v) {
        Snackbar.make(v, R.string.msg_marketing_guy_invents_new_feature, LENGTH_INDEFINITE)
                .setAction(R.string.scroll_to_the_end, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Context context = v.getContext();
                        while (!(context instanceof DemoActivity)) {
                            if (!(context instanceof ContextWrapper)) return;
                            context = ((ContextWrapper) context).getBaseContext();
                        }
                        ((DemoActivity) context).recyclerView.scrollToPosition(MAX_VALUE - 1);
                    }
                }).show();
    }
}
