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

package com.actinarium.nagbox.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.actinarium.nagbox.database.NagboxContract.BuildingBlocks;
import com.actinarium.nagbox.database.NagboxContract.TasksTable;
import com.actinarium.nagbox.model.Task;

/**
 * Database operations facade with transaction builder. Here's the place to put all insert/update/delete logic, as well
 * as query logic that's only needed in the service but not in content provider.
 * <p/>
 * In this particular project I made the class a static utility class. Feel free to make it non-static if you may need
 * to swap persistence implementations (e.g. SQLite and Firebase).
 *
 * @author Paul Danyliuk
 */
public final class NagboxDbOps {

    private NagboxDbOps() {}

    /**
     * Get a transaction builder for inserting/updating stuff
     *
     * @param writableDb Database
     * @return Transaction builder and executor
     * @see Transaction
     */
    public static Transaction startTransaction(SQLiteDatabase writableDb) {
        return new Transaction(writableDb);
    }

    /**
     * Get task by ID with given projection.
     *
     * @param db         Readable database
     * @param taskId     Task row ID
     * @param projection Projection to request this task with. Use this to limit only to the fields you need.
     * @return A new {@link Task} instance filled with data according to provided projection
     */
    public static Task getTaskStatusById(SQLiteDatabase db, long taskId, Projection<Task> projection) {
        Cursor cursor = db.query(
                TasksTable.TABLE_NAME,
                projection.getColumns(),
                BuildingBlocks.SELECTION_ID,
                new String[]{Long.toString(taskId)},
                null, null, null, "1"
        );
        Task task = cursor.moveToFirst() ? projection.mapCursorToModel(cursor, null) : null;
        cursor.close();
        return task;
    }

    /**
     * Query the maximum display order of all tasks. Required to determine the order of the next created task.
     *
     * @param db Readable database
     * @return maximum present display order, or 0 if there are zero rows in the DB
     */
    public static int getMaxTaskOrder(SQLiteDatabase db) {
        // Make an aggregated query for max(display_order) of all tasks
        Cursor cursor = db.query(
                TasksTable.TABLE_NAME,
                new String[]{BuildingBlocks.AGGR_COL_MAX_DISPLAY_ORDER},
                null, null, null, null, null
        );
        cursor.moveToFirst();
        final int result = cursor.isNull(0) ? 0 : cursor.getInt(0);
        cursor.close();
        return result;
    }

    /**
     * Query tasks table to determine the closest alarm to fire. Simply queries the minimum timestamp of active tasks
     * &mdash; since the timestamp is updated whenever it fires and/or the task gets active, there won't be leftover
     * timestamps in the past.
     *
     * @param db Readable database
     * @return timestamp of the next alarm to schedule, or 0 if no alarms are scheduled
     */
    public static long getClosestNagTimestamp(SQLiteDatabase db) {
        // Make an aggregated query for min(next_fire_at) where task is active
        Cursor cursor = db.query(
                TasksTable.TABLE_NAME,
                new String[]{BuildingBlocks.AGGR_COL_MIN_NEXT_FIRE_AT},
                BuildingBlocks.SELECTION_TASK_ACTIVE,
                null, null, null, null
        );
        cursor.moveToFirst();
        final long result = cursor.isNull(0) ? 0 : cursor.getLong(0);
        cursor.close();
        return result;
    }

    /**
     * Query all active tasks that need to be displayed in a notification: either due to fire, or already fired but not
     * "seen" yet (notification not dismissed). The tasks are ordered by nextFireAt time as it appears before updating
     * any of them (i.e. the first one would probably be the one that triggered this reminder, and the rest will be in
     * the order of further appearance, always running in cycles.
     *
     * @param db        Readable database
     * @param timestamp Current timestamp to only query tasks that have to fire (whose {@link Task#nextFireAt} <= this
     *                  timestamp)
     * @return array of tasks
     */
    public static Task[] getTasksToRemind(SQLiteDatabase db, long timestamp) {
        Cursor cursor = db.query(
                TasksTable.TABLE_NAME,
                NagboxContract.TASK_FULL_PROJECTION.getColumns(),
                BuildingBlocks.SELECTION_TASK_ACTIVE
                        + " AND (" + BuildingBlocks.SELECTION_TASK_NOT_SEEN
                        + " OR " + BuildingBlocks.SELECTION_TASK_FIRE_AT_ON_OR_BEFORE + ")",
                new String[]{Long.toString(timestamp)},
                null, null,
                BuildingBlocks.ORDER_BY_TASK_FIRE_AT_ASC
        );
        final int count = cursor.getCount();
        Task[] tasks = new Task[count];
        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            tasks[i] = NagboxContract.TASK_FULL_PROJECTION.mapCursorToModel(cursor, null);
        }
        cursor.close();
        return tasks;
    }

    /**
     * Query "not seen" tasks, either all or the one with specific ID
     *
     * @param db Readable database
     * @param id ID of a specific task, or {@link Task#NO_ID} to request all unseen tasks
     * @return array of tasks that are not seen
     */
    public static Task[] getTasksToDismiss(SQLiteDatabase db, long id) {
        Cursor cursor;
        if (id == Task.NO_ID) {
            cursor = db.query(
                    TasksTable.TABLE_NAME,
                    NagboxContract.TASK_FULL_PROJECTION.getColumns(),
                    BuildingBlocks.SELECTION_TASK_NOT_SEEN,
                    null, null, null, null
            );
        } else {
            cursor = db.query(
                    TasksTable.TABLE_NAME,
                    NagboxContract.TASK_FULL_PROJECTION.getColumns(),
                    BuildingBlocks.SELECTION_ID + " AND " + BuildingBlocks.SELECTION_TASK_NOT_SEEN,
                    new String[]{Long.toString(id)},
                    null, null, null
            );
        }
        final int count = cursor.getCount();
        Task[] tasks = new Task[count];
        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            tasks[i] = NagboxContract.TASK_FULL_PROJECTION.mapCursorToModel(cursor, null);
        }
        cursor.close();
        return tasks;
    }


    /**
     * Database insert/update transaction builder and executor. <b>Note:</b> the commands are actually executed as soon
     * as called, unlike with usual action builders where everything is executed in the end. This is so to make it
     * possible to obtain intermediate results. If an action fails, all calls to make further actions in this
     * transaction are suppressed.
     */
    public static class Transaction {

        private final SQLiteDatabase mDatabase;
        private boolean mIsSuccess;

        /**
         * Create a new transaction
         *
         * @param database Writable database obtained from {@link SQLiteOpenHelper#getWritableDatabase()}
         */
        public Transaction(SQLiteDatabase database) {
            mDatabase = database;
            mIsSuccess = true;
            mDatabase.beginTransaction();
        }

        /**
         * Get transaction result: <code>true</code> if all actions within this transaction has been successful so far,
         * and <code>false</code> if at least one has failed.
         *
         * @return intermediate result after the last performed action. Will always return <code>false</code> after the
         * transaction is committed.
         */
        public boolean getIntermediateResult() {
            return mIsSuccess;
        }

        /**
         * Commit the transaction.
         *
         * @return whether the transaction was performed successfully
         */
        public boolean commit() {
            if (mIsSuccess) {
                mDatabase.setTransactionSuccessful();
                mDatabase.endTransaction();
            }
            // Set mIsSuccess to false so that subsequent calls to the transaction are ignored
            boolean isRealSuccess = mIsSuccess;
            mIsSuccess = false;

            return isRealSuccess;
        }

        /**
         * Insert the task into the database.
         *
         * @param task Task to insert. If the operation is successful, {@link Task#id} will be set.
         * @return this for chaining
         */
        public Transaction createTask(Task task) {
            // Suppress the action if transaction is already failed
            if (!mIsSuccess) {
                return this;
            }

            long id = mDatabase.insert(
                    TasksTable.TABLE_NAME,
                    null,
                    task.toContentValues()
            );
            if (id != -1) {
                task.id = id;
            } else {
                mIsSuccess = false;
                mDatabase.endTransaction();
            }

            return this;
        }

        /**
         * Update the task. Only description fields exported in {@link Task#toContentValuesOnUpdate()} will be updated.
         *
         * @param task Task to update. Must have {@link Task#id} set.
         * @return this for chaining
         * @see #updateTaskStatus(Task)
         */
        public Transaction updateTask(Task task) {
            if (!mIsSuccess) {
                return this;
            }

            int rowsAffected = mDatabase.update(
                    TasksTable.TABLE_NAME,
                    task.toContentValuesOnUpdate(),
                    BuildingBlocks.SELECTION_ID,
                    new String[]{Long.toString(task.id)}
            );
            if (rowsAffected != 1) {
                mIsSuccess = false;
                mDatabase.endTransaction();
            }

            return this;
        }

        /**
         * Update task status, i.e. only status fields exported in {@link Task#toContentValuesOnStatusChange()}.
         *
         * @param task Task whose status to update. Must have {@link Task#id} set.
         * @return this for chaining
         */
        public Transaction updateTaskStatus(Task task) {
            if (!mIsSuccess) {
                return this;
            }

            int rowsAffected = mDatabase.update(
                    TasksTable.TABLE_NAME,
                    task.toContentValuesOnStatusChange(),
                    BuildingBlocks.SELECTION_ID,
                    new String[]{Long.toString(task.id)}
            );
            if (rowsAffected != 1) {
                mIsSuccess = false;
                mDatabase.endTransaction();
            }

            return this;
        }

        /**
         * Delete the task with given ID, or, to be precise, ensure that the task with given ID doesn't exist anymore.
         * This call won't fail even if there's nothing to delete (inspired by HTTP DELETE method behavior).
         *
         * @param taskId id of the {@link Task} to delete
         * @return this for chaining
         */
        public Transaction deleteTask(long taskId) {
            if (!mIsSuccess) {
                return this;
            }

            mDatabase.delete(
                    TasksTable.TABLE_NAME,
                    BuildingBlocks.SELECTION_ID,
                    new String[]{Long.toString(taskId)}
            );

            return this;
        }
    }

}
