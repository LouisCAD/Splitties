/*
 * Copyright (C) 2016 Actinarium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.actinarium.nagbox.common;

import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import com.actinarium.nagbox.R;

/**
 * Utility class with misc common UI functionality. I carry it over pretty much all of my projects.
 *
 * @author Paul Danyliuk
 */
public final class ViewUtils {

    private ViewUtils() {}

    /**
     * Sets up toolbar as action bar, based on Persistence-specific assumptions
     *
     * @param activity  Activity, will be casted to AppCompatActivity
     * @param rootView  View where to search for <code>+@id/toolbar</code>
     * @param title     String resource, can be 0 to take string resource from activity
     * @param elevation Dimension resource, can be 0 for no elevation
     * @return Decorated action bar, in case any other changes are required
     */
    public static ActionBar setupToolbar(@NonNull AppCompatActivity activity, View rootView, @StringRes int title, @DimenRes int elevation) {
        final Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        final ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar == null) {
            throw new AssertionError("Support ActionBar is null for unknown reason");
        }

        if (title != 0) {
            actionBar.setTitle(title);
        }
        if (elevation != 0) {
            actionBar.setElevation(activity.getResources().getDimension(elevation));
        }
        return actionBar;
    }

    /**
     * Set custom icon and color for recents screen card
     *
     * @param activity Activity to configure
     * @param colorRes Color resource for recents screen card title
     * @param icon     Bitmap drawable resource to draw into recents screen card title
     */
    public static void setupRecentsIcon(Activity activity, @ColorRes int colorRes, @DrawableRes int icon) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }

        int color = ContextCompat.getColor(activity, colorRes);
        Bitmap bm = BitmapFactory.decodeResource(activity.getResources(), icon);
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, bm, color);

        activity.setTaskDescription(td);
        bm.recycle();
    }

}
