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

package xyz.louiscad.selectableviewgroups.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;

enum SelectableViewGroupsUtils {
    ;
    private static final int[] SELECTABLE_ATTR = {android.R.attr.selectableItemBackground};

    static Drawable getDefaultForegroundSelector(Context context) {
        final TypedArray ta = context.obtainStyledAttributes(SELECTABLE_ATTR);
        try {
            return ta.getDrawable(0);
        } finally {
            ta.recycle();
        }
    }
}
