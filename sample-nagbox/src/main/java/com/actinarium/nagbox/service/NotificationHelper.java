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

package com.actinarium.nagbox.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import com.actinarium.nagbox.R;
import com.actinarium.nagbox.common.DateUtils;
import com.actinarium.nagbox.model.Task;
import com.actinarium.nagbox.ui.MainActivity;

/**
 * Just an utility class to build and fire nag notification(s). Moved here to unclutter the service.
 *
 * @author Paul Danyliuk
 */
public final class NotificationHelper {

    private static final int NAG_NOTIFICATION_ID = 0;
    private static final String NAG_NOTIFICATION_GROUP = "nagbox";

    private static final String INBOX_STYLE_LINE_FORMAT_L = "<font face=\"sans-serif-medium\">%1$s</font> %2$s";
    private static final String INBOX_STYLE_LINE_FORMAT_OLD = "<b>%1$s</b> %2$s";

    private NotificationHelper() {}

    /**
     * Build and display a notification for provided task(s)
     *
     * @param context Context
     * @param tasks   Array of tasks, must contain at least one item
     */
    public static void fireNotification(Context context, Task[] tasks) {
        if (tasks.length == 1) {
            fireForSingleTask(context, tasks[0]);
        } else {
            fireForMultipleTasks(context, tasks);
        }
    }

    private static void fireForSingleTask(Context context, Task task) {
        long currentTime = System.currentTimeMillis();

        // Notification action to stop the task
        Intent stopAction = new Intent(context, NagboxService.class);
        stopAction.setAction(NagboxService.ACTION_ON_NOTIFICATION_ACTION_STOP_TASK);
        stopAction.putExtra(NagboxService.EXTRA_TASK_ID, task.id);
        stopAction.putExtra(NagboxService.EXTRA_CANCEL_NOTIFICATION_ID, NAG_NOTIFICATION_ID);
        PendingIntent stopActionPI = PendingIntent.getService(context, 0, stopAction, PendingIntent.FLAG_UPDATE_CURRENT);

        // Create public notification
        Notification publicNotification = makeCommonBuilder(context, currentTime, task.id)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getResources().getQuantityString(R.plurals.notification_stacked_header, 1, 1))
                .build();

        // Create private notification
        Notification privateNotification = makeCommonBuilder(context, currentTime, task.id)
                .setPublicVersion(publicNotification)
                .setContentTitle(task.title)
                .setContentText(DateUtils.prettyPrintNagDuration(context, task.lastStartedAt, currentTime))
                .addAction(R.drawable.ic_cancel, context.getString(R.string.notification_action_stop), stopActionPI)
                .build();

        // Fire!
        NotificationManagerCompat.from(context).notify(NAG_NOTIFICATION_ID, privateNotification);
    }

    private static void fireForMultipleTasks(Context context, Task[] tasks) {
        long currentTime = System.currentTimeMillis();
        final NotificationManagerCompat notifManager = NotificationManagerCompat.from(context);
        final int tasksCount = tasks.length;

        // Create a group of stacked notifications
        for (Task task : tasks) {

            // Notification action to stop the task
            Intent stopAction = new Intent(context, NagboxService.class);
            stopAction.setAction(NagboxService.ACTION_ON_NOTIFICATION_ACTION_STOP_TASK);
            stopAction.putExtra(NagboxService.EXTRA_TASK_ID, task.id);
            stopAction.putExtra(NagboxService.EXTRA_CANCEL_NOTIFICATION_ID, (int) task.id);

            // Since actions are "equal" (differ in extras only), need to use a unique "request code" (task.id will do)
            PendingIntent stopActionPI = PendingIntent.getService(context, (int) task.id, stopAction, PendingIntent.FLAG_UPDATE_CURRENT);

            Notification stackedItem = makeCommonBuilder(context, currentTime, task.id)
                    .setContentTitle(task.title)
                    .setContentText(DateUtils.prettyPrintNagDuration(context, task.lastStartedAt, currentTime))
                    .setGroup(NAG_NOTIFICATION_GROUP)
                    .addAction(R.drawable.ic_cancel, context.getString(R.string.notification_action_stop), stopActionPI)
                    .build();

            // Well, let's use task IDs for their individual notifications then
            notifManager.notify((int) task.id, stackedItem);
        }

        // Summary text, reused
        final String summary = context.getResources()
                .getQuantityString(R.plurals.notification_stacked_header, tasksCount, tasksCount);

        // Public summary notification
        // Since Android N already shows app name in the notification, display the summary in title
        boolean isApi24 = Build.VERSION.SDK_INT >= 24;
        Notification publicNotification = makeCommonBuilder(context, currentTime, Task.NO_ID)
                .setContentTitle(isApi24 ? summary : context.getString(R.string.app_name))
                .setContentText(isApi24 ? null : summary)
                .setGroup(NAG_NOTIFICATION_GROUP)
                .setGroupSummary(true)
                .build();

        // Create expanded (inbox style) for private notification.
        // If there are more than 5 tasks, enlist first 4 and add +x more
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle(summary);
        if (tasksCount <= 5) {
            for (Task task : tasks) {
                inboxStyle.addLine(makeInboxStyleLine(context, task, currentTime));
            }
        } else {
            for (int i = 0; i < 4; i++) {
                inboxStyle.addLine(makeInboxStyleLine(context, tasks[i], currentTime));
            }
            inboxStyle.addLine(context.getString(R.string.notification_stack_overflow, tasksCount - 4));
        }

        // Create private summary notification
        Notification privateNotification = makeCommonBuilder(context, currentTime, Task.NO_ID)
                .setPublicVersion(publicNotification)
                .setContentTitle(isApi24 ? summary : context.getString(R.string.app_name))
                .setContentText(isApi24 ? null : summary)
                .setStyle(inboxStyle)
                .setGroup(NAG_NOTIFICATION_GROUP)
                .setGroupSummary(true)
                .build();

        notifManager.notify(NAG_NOTIFICATION_ID, privateNotification);
    }

    /**
     * Cancel the notification by provided ID. On Android N will also cancel the summary notification if it's the last
     * child being removed.
     *
     * @param context        context
     * @param notificationId ID of the notification to cancel
     */
    public static void cancelNotification(Context context, int notificationId) {
        final NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.cancel(notificationId);

        // Apparently N doesn't cancel summary notification when all individual ones are cancelled
        // So we have to do it ourselves
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            final StatusBarNotification[] notifs = manager.getActiveNotifications();
            if (notifs.length == 1 && notifs[0].getId() == NAG_NOTIFICATION_ID && notifs[0].isGroup()) {
                // This must be the remaining summary. Clear it away
                managerCompat.cancel(NAG_NOTIFICATION_ID);
            }
        }
    }

    /**
     * Make a new notification builder with common attributes already set.
     *
     * @param context         context
     * @param currentTime     timestamp to display in the notification
     * @param dismissedTaskId ID of the task to dismiss with this notification, or {@link Task#NO_ID} to dismiss all
     * @return partially pre-configured notification builder
     */
    private static NotificationCompat.Builder makeCommonBuilder(Context context, long currentTime, long dismissedTaskId) {
        // When notification is clicked, simply go to the app
        Intent primaryAction = new Intent(context, MainActivity.class);
        PendingIntent primaryActionPI = PendingIntent.getActivity(context, 0, primaryAction, PendingIntent.FLAG_UPDATE_CURRENT);

        // When notification is dismissed, tell the service to handle it properly
        Intent dismissAction = new Intent(context, NagboxService.class);
        dismissAction.setAction(NagboxService.ACTION_ON_NOTIFICATION_DISMISSED);
        dismissAction.putExtra(NagboxService.EXTRA_TASK_ID, dismissedTaskId);
        // Just as well, this pending intent needs a unique request code, otherwise it will be overwritten
        PendingIntent dismissActionPI = PendingIntent.getService(context, (int) dismissedTaskId, dismissAction, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_nag)
                .setColor(ContextCompat.getColor(context, R.color.primaryDark))
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(primaryActionPI)
                .setDeleteIntent(dismissActionPI)
                .setAutoCancel(true)
                .setWhen(currentTime)
                .setShowWhen(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);
    }

    private static CharSequence makeInboxStyleLine(Context context, Task task, long now) {
        final String duration = DateUtils.prettyPrintNagDuration(context, task.lastStartedAt, now);
        if (Build.VERSION.SDK_INT >= 24) {
            return Html.fromHtml(String.format(INBOX_STYLE_LINE_FORMAT_L, task.title, duration), Html.FROM_HTML_MODE_LEGACY);
        } else if (Build.VERSION.SDK_INT >= 21) {
            //noinspection deprecation
            return Html.fromHtml(String.format(INBOX_STYLE_LINE_FORMAT_L, task.title, duration));
        } else {
            //noinspection deprecation
            return Html.fromHtml(String.format(INBOX_STYLE_LINE_FORMAT_OLD, task.title, duration));
        }
    }
}
