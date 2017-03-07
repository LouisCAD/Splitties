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
import android.support.annotation.NonNull;
import android.support.v7.widget.SwitchCompat;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import xyz.louiscad.selectableviewgroups.widget.SelectableConstraintLayout;

public class TwoLinesIconSwitchListItem extends SelectableConstraintLayout {

    public TwoLinesIconSwitchListItem(Context context) {
        super(context);
        init(context);
    }

    public TwoLinesIconSwitchListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TwoLinesIconSwitchListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private ImageView icon;
    private TextView firstLine, secondLine;
    private SwitchCompat toggle;

    private void init(final Context context) {
        inflate(context, R.layout.content_list_item_two_lines_icon_switch, this);
        icon = (ImageView) findViewById(R.id.icon);
        firstLine = (TextView) findViewById(R.id.firstLine);
        secondLine = (TextView) findViewById(R.id.secondLine);
        toggle = (SwitchCompat) findViewById(R.id.toggle);
    }

    @NonNull
    public ImageView getIcon() {
        return icon;
    }

    @NonNull
    public TextView getFirstLine() {
        return firstLine;
    }

    @NonNull
    public TextView getSecondLine() {
        return secondLine;
    }

    @NonNull
    public SwitchCompat getToggle() {
        return toggle;
    }
}
