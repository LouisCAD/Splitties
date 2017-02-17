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

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.Nullable;
import com.actinarium.nagbox.model.Task;

/**
 * Nagbox database contract class
 *
 * @author Paul Danyliuk
 */
public final class NagboxContract {

    public static final String CONTENT_AUTHORITY = "com.actinarium.nagbox.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_TASKS = "tasks";

    // Table mappings ------------------------------------

    public static class TasksTable implements BaseColumns {

        // Database stuff
        public static final String TABLE_NAME = "tasks";

        public static final String COL_TITLE = "title";
        public static final String COL_INTERVAL = "interval";
        public static final String COL_FLAGS = "flags";
        public static final String COL_NEXT_FIRE_AT = "next_fire_at";
        public static final String COL_LAST_STARTED_AT = "last_started_at";
        public static final String COL_DISPLAY_ORDER = "display_order";

        // Content provider stuff
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
        public static final String CONTENT_TYPE_DIR = "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + '.' + PATH_TASKS;

        public static Uri getUriForItem(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    // Building blocks -----------------------------------

    /**
     * An interface or a class (if methods are required) where various building blocks are grouped.
     * Selection and sorting expressions, join clauses etc belong here.
     */
    public interface BuildingBlocks {

        String SELECTION_ID = BaseColumns._ID + " = ?";
        String SELECTION_TASK_ACTIVE = TasksTable.COL_FLAGS + " & " + Task.FLAG_ACTIVE;
        String SELECTION_TASK_NOT_SEEN = TasksTable.COL_FLAGS + " & " + Task.FLAG_NOT_SEEN;
        String SELECTION_TASK_FIRE_AT_ON_OR_BEFORE = TasksTable.COL_NEXT_FIRE_AT + " <= ?";

        String AGGR_COL_MIN_NEXT_FIRE_AT = "MIN(" + TasksTable.COL_NEXT_FIRE_AT + ")";
        String AGGR_COL_MAX_DISPLAY_ORDER = "MAX(" + TasksTable.COL_DISPLAY_ORDER + ")";

        String ORDER_BY_DISPLAY_ORDER_ASC = TasksTable.COL_DISPLAY_ORDER + " ASC";
        String ORDER_BY_TASK_FIRE_AT_ASC = TasksTable.COL_NEXT_FIRE_AT + " ASC";

    }

    // Projections ---------------------------------------

    public static final TaskFullProjection TASK_FULL_PROJECTION = new TaskFullProjection();
    public static final TaskStatusProjection TASK_STATUS_PROJECTION = new TaskStatusProjection();

    /**
     * A projection to get all Task fields.
     */
    public static final class TaskFullProjection implements Projection<Task> {

        private static final String[] COLUMNS = {
                TasksTable._ID,
                TasksTable.COL_TITLE,
                TasksTable.COL_INTERVAL,
                TasksTable.COL_FLAGS,
                TasksTable.COL_NEXT_FIRE_AT,
                TasksTable.COL_LAST_STARTED_AT,
                TasksTable.COL_DISPLAY_ORDER
        };

        @Override
        public String[] getColumns() {
            return COLUMNS;
        }

        @Override
        public Task mapCursorToModel(Cursor cursor, @Nullable Task task) {
            if (task == null) {
                task = new Task();
            }

            task.id = cursor.getLong(0);
            task.title = cursor.getString(1);
            task.interval = cursor.getInt(2);
            task.flags = cursor.getInt(3);
            task.nextFireAt = cursor.getLong(4);
            task.lastStartedAt = cursor.getLong(5);
            task.displayOrder = cursor.getInt(6);

            return task;
        }

        @Override
        public long getId(Cursor cursor) {
            return cursor.getLong(0);
        }
    }

    /**
     * A projection to get only task status fields.
     */
    public static final class TaskStatusProjection implements Projection<Task> {

        private static final String[] COLUMNS = {
                TasksTable._ID,
                TasksTable.COL_FLAGS,
                TasksTable.COL_NEXT_FIRE_AT,
                TasksTable.COL_LAST_STARTED_AT
        };

        @Override
        public String[] getColumns() {
            return COLUMNS;
        }

        @Override
        public Task mapCursorToModel(Cursor cursor, @Nullable Task task) {
            if (task == null) {
                task = new Task();
            }

            task.id = cursor.getLong(0);
            task.flags = cursor.getInt(1);
            task.nextFireAt = cursor.getLong(2);
            task.lastStartedAt = cursor.getLong(3);

            return task;
        }

        @Override
        public long getId(Cursor cursor) {
            return cursor.getLong(0);
        }
    }

}
