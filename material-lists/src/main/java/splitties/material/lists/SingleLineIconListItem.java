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

package splitties.material.lists;

import android.content.Context;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.louiscad.selectableviewgroups.widget.SelectableLinearLayout;

public class SingleLineIconListItem extends SelectableLinearLayout {
    private ImageView icon;
    private TextView firstLine;

    public SingleLineIconListItem(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SingleLineIconListItem(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SingleLineIconListItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SingleLineIconListItem(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(final Context context) {
        inflate(context, R.layout.content_list_item_single_line_icon, this);
        icon = (ImageView) findViewById(R.id.icon);
        firstLine = (TextView) findViewById(R.id.firstLine);
    }

    @NonNull
    public ImageView getIcon() {
        return icon;
    }

    /**
     * The single-line list item keeps the "firstLine" name for it's only TextView to make
     * switching to {@link TwoLinesIconListItem} easier.
     */
    @NonNull
    public TextView getFirstLine() {
        return firstLine;
    }

}
