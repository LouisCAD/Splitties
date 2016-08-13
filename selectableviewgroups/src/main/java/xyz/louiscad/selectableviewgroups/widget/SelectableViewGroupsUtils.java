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
