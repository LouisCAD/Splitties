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

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.text.format.DateUtils;
import android.util.Log;
import com.actinarium.nagbox.database.NagboxContract;
import com.actinarium.nagbox.database.NagboxContract.TasksTable;
import com.actinarium.nagbox.database.NagboxDbHelper;
import com.actinarium.nagbox.database.NagboxDbOps;
import com.actinarium.nagbox.model.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * An intent service that handles task operations and alarm management.
 *
 * @author Paul Danyliuk
 */
public class NagboxService extends IntentService {

    private static final String TAG = "NagboxService";

    public static final String ACTION_CREATE_TASK = "com.actinarium.nagbox.intent.action.CREATE_TASK";
    public static final String ACTION_UPDATE_TASK = "com.actinarium.nagbox.intent.action.UPDATE_TASK";
    public static final String ACTION_UPDATE_TASK_STATUS = "com.actinarium.nagbox.intent.action.UPDATE_TASK_STATUS";
    public static final String ACTION_DELETE_TASK = "com.actinarium.nagbox.intent.action.DELETE_TASK";
    public static final String ACTION_RESTORE_TASK = "com.actinarium.nagbox.intent.action.RESTORE_TASK";

    // These can only be triggered within the system (have no corresponding public ways to call them)
    static final String ACTION_ON_ALARM_FIRED = "com.actinarium.nagbox.intent.action.ON_ALARM_FIRED";
    static final String ACTION_ON_NOTIFICATION_DISMISSED = "com.actinarium.nagbox.intent.action.ON_NOTIFICATION_DISMISSED";
    static final String ACTION_ON_NOTIFICATION_ACTION_STOP_TASK = "com.actinarium.nagbox.intent.action.ON_NOTIFICATION_ACTION_STOP_TASK";

    static final String EXTRA_TASK = "com.actinarium.nagbox.intent.extra.TASK";
    static final String EXTRA_TASK_ID = "com.actinarium.nagbox.intent.extra.TASK_ID";
    static final String EXTRA_CANCEL_NOTIFICATION_ID = "com.actinarium.nagbox.intent.extra.EXTRA_CANCEL_NOTIFICATION_ID";

    private static final long ALARM_TOLERANCE = 5 * DateUtils.SECOND_IN_MILLIS;

    /**
     * Our writable database. Since we need it literally everywhere, it makes sense to pull it only once in onCreate().
     */
    private SQLiteDatabase mDatabase;

    /**
     * Create a new unstarted task. Doesn't trigger rescheduling alarms.
     *
     * @param context context
     * @param task    task to create
     */
    public static void createTask(Context context, Task task) {
        Intent intent = new Intent(context, NagboxService.class);
        intent.setAction(ACTION_CREATE_TASK);
        intent.putExtra(EXTRA_TASK, task);
        context.startService(intent);
    }

    /**
     * Update task description. Doesn't update the flags (i.e. doesn't start or stop the task), and as of now it doesn't
     * reschedule next alarm either. If you need to update task status, use {@link #updateTaskStatus(Context, Task)}.
     * {@link Task#id} must be set.
     *
     * @param context context
     * @param task    task to update
     */
    public static void updateTask(Context context, Task task) {
        Intent intent = new Intent(context, NagboxService.class);
        intent.setAction(ACTION_UPDATE_TASK);
        intent.putExtra(EXTRA_TASK, task);
        context.startService(intent);
    }

    /**
     * Update task status (flags). Use this to start or stop the task. {@link Task#id} must be set. Will result in
     * rescheduling the alarm to closer time if needed.
     *
     * @param context context
     * @param task    task to update its flags
     */
    public static void updateTaskStatus(Context context, Task task) {
        Intent intent = new Intent(context, NagboxService.class);
        intent.setAction(ACTION_UPDATE_TASK_STATUS);
        intent.putExtra(EXTRA_TASK, task);
        context.startService(intent);
    }

    /**
     * Delete the task entirely. Will trigger rescheduling the alarm to later time if needed, or cancelling it.
     *
     * @param context context
     * @param taskId  ID of the task to delete
     */
    public static void deleteTask(Context context, long taskId) {
        Intent intent = new Intent(context, NagboxService.class);
        intent.setAction(ACTION_DELETE_TASK);
        intent.putExtra(EXTRA_TASK_ID, taskId);
        context.startService(intent);
    }

    /**
     * Same as {@link #createTask(Context, Task)}, but will insert the task with its old ID and trigger rescheduling the
     * alarm.
     *
     * @param context context
     * @param task    task to restore
     */
    public static void restoreTask(Context context, Task task) {
        Intent intent = new Intent(context, NagboxService.class);
        intent.setAction(ACTION_RESTORE_TASK);
        intent.putExtra(EXTRA_TASK, task);
        context.startService(intent);
    }


    public NagboxService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = NagboxDbHelper.getInstance(this).getWritableDatabase();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent == null) {
            return;
        }

        // I know that only either of those is needed, but for the sake of nice code I'm pulling these here
        final Task task = intent.getParcelableExtra(EXTRA_TASK);
        final long id = intent.getLongExtra(EXTRA_TASK_ID, Task.NO_ID);
        switch (intent.getAction()) {
            case ACTION_UPDATE_TASK_STATUS:
                handleUpdateTaskStatus(task);
                break;
            case ACTION_ON_ALARM_FIRED:
                handleOnAlarmFired();
                break;
            case ACTION_ON_NOTIFICATION_DISMISSED:
                handleOnNotificationDismissed(id);
                break;
            case ACTION_ON_NOTIFICATION_ACTION_STOP_TASK:
                int notificationIdToCancel = intent.getIntExtra(EXTRA_CANCEL_NOTIFICATION_ID, -1);
                handleStopTaskById(id, notificationIdToCancel);
                break;
            case ACTION_CREATE_TASK:
                handleCreateTask(task);
                break;
            case ACTION_UPDATE_TASK:
                handleUpdateTask(task);
                break;
            case ACTION_DELETE_TASK:
                handleDeleteTask(id);
                break;
            case ACTION_RESTORE_TASK:
                handleRestoreTask(task);
                break;
        }

        // Release the wake lock, if there was any.
        WakefulBroadcastReceiver.completeWakefulIntent(intent);
    }


    private void handleCreateTask(Task task) {
        // Our app must ensure that task order is correct and unique. So assign the order = max(order) + 1
        // We could (and should) do this atomically using INSERT with sub-query, but that's not trivial with given APIs.
        int maxOrder = NagboxDbOps.getMaxTaskOrder(mDatabase);
        task.displayOrder = maxOrder + 1;

        // In the end of the method we put everything into the DB using DbOps.Transaction
        boolean isSuccess = NagboxDbOps.startTransaction(mDatabase)
                .createTask(task)
                .commit();

        // Process transaction result.
        // If successful, you still need to notify the cursor so that any loaders that listen to this data would reload
        if (isSuccess) {
            getContentResolver().notifyChange(TasksTable.CONTENT_URI, null);
        } else {
            Log.e(TAG, "Couldn't create task " + task);
        }
    }

    private void handleUpdateTask(Task task) {
        if (task.id < 0) {
            Log.e(TAG, "Was trying to update task with invalid/unset ID=" + task.id);
            return;
        }

        boolean isSuccess = NagboxDbOps.startTransaction(mDatabase)
                .updateTask(task)
                .commit();

        if (isSuccess) {
            // Even though our content provider doesn't know about a single item URI yet, won't hurt to do it right
            getContentResolver().notifyChange(TasksTable.getUriForItem(task.id), null);
        } else {
            Log.e(TAG, "Couldn't update task " + task);
        }
    }

    private void handleUpdateTaskStatus(Task task) {
        if (task.id < 0) {
            Log.e(TAG, "Was trying to update flags of the task with invalid/unset ID=" + task.id);
            return;
        }

        boolean isSuccess = NagboxDbOps.startTransaction(mDatabase)
                .updateTaskStatus(task)
                .commit();

        if (isSuccess) {
            getContentResolver().notifyChange(TasksTable.getUriForItem(task.id), null);
            rescheduleAlarm();
        } else {
            Log.e(TAG, "Couldn't update status of task " + task);
        }
    }

    private void handleStopTaskById(long taskId, int notificationIdToCancel) {
        if (notificationIdToCancel != -1) {
            NotificationHelper.cancelNotification(this, notificationIdToCancel);
        }

        // Request partial task model - only status columns (id, flags, next timestamp) are needed
        Task task = NagboxDbOps.getTaskStatusById(mDatabase, taskId, NagboxContract.TASK_STATUS_PROJECTION);

        if (task == null || !task.isActive()) {
            // Nothing to update
            return;
        }

        task.setIsActive(false);
        task.setIsSeen(true);
        handleUpdateTaskStatus(task);
    }

    private void handleDeleteTask(long taskId) {
        if (taskId < 0) {
            Log.e(TAG, "Was trying to delete task with invalid ID=" + taskId);
            return;
        }

        boolean isSuccess = NagboxDbOps.startTransaction(mDatabase)
                .deleteTask(taskId)
                .commit();

        if (isSuccess) {
            getContentResolver().notifyChange(TasksTable.getUriForItem(taskId), null);
            rescheduleAlarm();
        } else {
            Log.e(TAG, "Couldn't delete task with ID " + taskId);
        }
    }

    private void handleRestoreTask(Task task) {
        // Restoring is the same as creating, and our task already has an order field set correctly, and an ID to notify
        boolean isSuccess = NagboxDbOps.startTransaction(mDatabase)
                .createTask(task)
                .commit();

        if (isSuccess) {
            getContentResolver().notifyChange(TasksTable.getUriForItem(task.id), null);
            rescheduleAlarm();
        } else {
            Log.e(TAG, "Couldn't restore task " + task);
        }
    }

    private void rescheduleAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Prepare pending intent. Setting, updating, or cancelling the alarm - we need it in either case
        Intent intent = new Intent(this, NagAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        long nextTimestamp = NagboxDbOps.getClosestNagTimestamp(mDatabase);
        if (nextTimestamp == 0) {
            alarmManager.cancel(pendingIntent);
        } else {
            // todo: deal with exact/inexact reminders later
            if (Build.VERSION.SDK_INT >= 23) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextTimestamp, pendingIntent);
            } else if (Build.VERSION.SDK_INT >= 19) {
                alarmManager.setWindow(AlarmManager.RTC_WAKEUP, nextTimestamp, ALARM_TOLERANCE, pendingIntent);
            } else {
                alarmManager.set(AlarmManager.RTC_WAKEUP, nextTimestamp, pendingIntent);
            }
        }
    }

    private void handleOnAlarmFired() {
        final long now = System.currentTimeMillis();

        Task[] tasksToRemind = NagboxDbOps.getTasksToRemind(mDatabase, now);
        if (tasksToRemind.length == 0) {
            Log.i(TAG, "Alarm fired/check requested, but there was nothing to remind about");
            return;
        }

        NotificationHelper.fireNotification(this, tasksToRemind);

        // Update the status and the time of the next fire where needed.
        List<Task> tasksToUpdate = new ArrayList<>(tasksToRemind.length);
        for (Task task : tasksToRemind) {
            boolean isModified = false;
            if (task.isSeen()) {
                task.setIsSeen(false);
                isModified = true;
            }

            // Using the loop because the alarm might've fired long ago (e.g. before system reboot),
            // so we need to make sure that nextFireAt is indeed in the future
            while (task.nextFireAt <= now) {
                task.nextFireAt += task.interval * DateUtils.MINUTE_IN_MILLIS;
                isModified = true;
            }

            if (isModified) {
                tasksToUpdate.add(task);
            }
        }

        final int updateSize = tasksToUpdate.size();
        if (updateSize == 0) {
            Log.w(TAG, "Strangely enough, there was nothing to update when alarm fired");
            rescheduleAlarm();
            return;
        }

        // Otherwise update the tasks that need it
        NagboxDbOps.Transaction transaction = NagboxDbOps.startTransaction(mDatabase);
        for (int i = 0; i < updateSize; i++) {
            final Task task = tasksToUpdate.get(i);
            transaction.updateTaskStatus(task);
        }
        boolean isSuccess = transaction.commit();

        if (!isSuccess) {
            Log.e(TAG, "Couldn't update status of the tasks when alarm fired");
        } else {
            // Notify all affected task items
            final ContentResolver contentResolver = getContentResolver();
            for (int i = 0; i < updateSize; i++) {
                contentResolver.notifyChange(TasksTable.getUriForItem(tasksToUpdate.get(i).id), null);
            }
        }

        // Finally, schedule the alarm to fire the next time it's ought to fire
        rescheduleAlarm();
    }

    /**
     * Unset "not seen" flag from the task with provided ID or all unseen tasks (depending on whether a summary
     * notification or an individual one from the stack was dismissed)
     *
     * @param id ID of the task that's "seen". Pass {@link Task#NO_ID} to "see" all tasks
     */
    private void handleOnNotificationDismissed(long id) {
        Task[] tasksToDismiss = NagboxDbOps.getTasksToDismiss(mDatabase, id);

        if (tasksToDismiss.length == 0) {
            // Well, nothing to do. Maybe the user has deactivated the tasks before dismissing the notification
            return;
        }

        NagboxDbOps.Transaction transaction = NagboxDbOps.startTransaction(mDatabase);
        for (Task task : tasksToDismiss) {
            task.setIsSeen(true);
            transaction.updateTaskStatus(task);
        }
        boolean isSuccess = transaction.commit();

        if (!isSuccess) {
            Log.e(TAG, "Couldn't unset the 'not seen' flag from tasks");
        } else {
            // Notify all affected task items
            final ContentResolver contentResolver = getContentResolver();
            for (Task task : tasksToDismiss) {
                contentResolver.notifyChange(TasksTable.getUriForItem(task.id), null);
            }
        }
    }

}
