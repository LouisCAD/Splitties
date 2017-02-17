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

import android.content.Context;
import android.os.Build;
import android.text.format.DateFormat;
import com.actinarium.nagbox.R;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Utility class with common date/time formatting functionality.
 *
 * @author Paul Danyliuk
 */
public final class DateUtils {

    private DateUtils() {}

    /**
     * Attempts at pretty-printing date and time. If provided calendar is today, shows "Started at {time}", otherwise
     * shows "Started on {date} at {time}". {date} and {time} are picked the best possible for current locale and
     * settings.
     *
     * @param context   context
     * @param timestamp instant to render
     * @return pretty printed time/date
     */
    public static String prettyPrintStartTime(Context context, long timestamp) {
        // todo: consider replacing this with android.text.format.DateUtils
        Calendar now = GregorianCalendar.getInstance();
        Calendar then = GregorianCalendar.getInstance();
        then.setTimeInMillis(timestamp);
        String formatSkeleton;
        // Show or omit month and day depending on whether the date is in current year
        if (then.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
            if (then.get(Calendar.DAY_OF_YEAR) == now.get(Calendar.DAY_OF_YEAR)) {
                // It's today, no need to show month and day
                return context.getString(
                        R.string.status_started_at,
                        DateFormat.getTimeFormat(context).format(then.getTime())
                );
            } else {
                formatSkeleton = "MMM d";
            }
        } else {
            formatSkeleton = "MMM d, yyyy";
        }
        // Generate best format based on recommendation skeleton, if device supports it
        String bestFormatString;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            bestFormatString = DateFormat.getBestDateTimePattern(Locale.getDefault(), formatSkeleton);
        } else {
            bestFormatString = formatSkeleton;
        }
        String dateString = DateFormat.format(bestFormatString, then).toString();
        String timeString = DateFormat.getTimeFormat(context).format(then.getTime());
        return context.getString(R.string.status_started_on_at, dateString, timeString);
    }

    /**
     * Pretty prints nag duration for the notification. For under 60 minutes, shows full minutes. For exact hours,
     * shows message with only hours. Otherwise shows in format "for 2h 30m already".
     *
     * @param context context
     * @param since   when the task started
     * @param to      when the notification fired
     * @return pretty printed message
     */
    public static String prettyPrintNagDuration(Context context, long since, long to) {
        int fullMinutes = (int) ((to - since) / android.text.format.DateUtils.MINUTE_IN_MILLIS);
        if (fullMinutes < 60) {
            return context.getResources()
                    .getQuantityString(R.plurals.notification_nag_duration_minutes, fullMinutes, fullMinutes);
        } else {
            int hours = fullMinutes % 60;
            int minutes = fullMinutes / 60;
            if (minutes == 0) {
                return context.getResources()
                        .getQuantityString(R.plurals.notification_nag_duration_hours, hours, hours);
            } else {
                return context.getString(R.string.notification_nag_duration_hours_minutes, hours, minutes);
            }
        }
    }

}
