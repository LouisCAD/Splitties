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

import android.database.Cursor;
import android.provider.BaseColumns;

/**
 * Convenience util class to read data from cursor row by column names. This is inferior to reading directly by known
 * index because it requires collecting the map of name->index (intrinsically within Cursor) and then doing lookups. Use
 * known projections instead where possible.
 *
 * @author Paul Danyliuk
 */
public final class CursorReaderUtils {

    private CursorReaderUtils() {}

    /**
     * Get blob from given column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return array of bytes, or null if the column doesn't exist.
     */
    public static byte[] getBlob(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return null;
        } else {
            return cursor.getBlob(index);
        }
    }

    /**
     * Get string value from given column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return String object, or null if the column doesn't exist.
     */
    public static String getString(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return null;
        } else {
            return cursor.getString(index);
        }
    }

    /**
     * Get primitive integer value from given column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return integer value, or 0 if the column doesn't exist.
     */
    public static int getInt(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return 0;
        } else {
            return cursor.getInt(index);
        }
    }

    /**
     * Get boxed Integer value from a nullable column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return boxed Integer value, or null if the value is null or the column doesn't exist.
     */
    public static Integer getNullableInt(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return null;
        } else {
            return cursor.isNull(index) ? null : cursor.getInt(index);
        }
    }

    /**
     * Get row ID (column with name <code>_id</code>) at current position of a given cursor
     *
     * @param cursor    cursor positioned at the data row to read from
     * @param tableName table name, required to resolve reading row ID from joined tables
     * @return row ID, or -1 if the column doesn't exist.
     */
    public static long getId(Cursor cursor, String tableName) {
        // Workaround for Android bug 903852 (see https://code.google.com/p/android/issues/detail?id=7201)
        int index = cursor.getColumnIndex(tableName + "_" + BaseColumns._ID);
        if (index == -1) {
            index = cursor.getColumnIndex(BaseColumns._ID);
            if (index == -1) {
                return -1;
            }
        }
        return cursor.getLong(index);
    }

    /**
     * Get primitive long value from given column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return long value, or 0 if the column doesn't exist.
     */
    public static long getLong(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return 0;
        } else {
            return cursor.getLong(index);
        }
    }

    /**
     * Get boxed Long value from a nullable column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return boxed Long value, or null if the value is null or the column doesn't exist.
     */
    public static Long getNullableLong(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return null;
        } else {
            return cursor.isNull(index) ? null : cursor.getLong(index);
        }
    }

    /**
     * Get primitive float value from given column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return float value, or 0 if the column doesn't exist.
     */
    public static float getFloat(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return 0;
        } else {
            return cursor.getFloat(index);
        }
    }

    /**
     * Get boxed Float value from a nullable column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return boxed Float value, or null if the value is null or the column doesn't exist.
     */
    public static Float getNullableFloat(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return null;
        } else {
            return cursor.isNull(index) ? null : cursor.getFloat(index);
        }
    }

    /**
     * Get primitive double value from given column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return double value, or 0 if the column doesn't exist.
     */
    public static double getDouble(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return 0;
        } else {
            return cursor.getDouble(index);
        }
    }

    /**
     * Get boxed Double value from a nullable column of a given cursor at current position
     *
     * @param cursor     cursor positioned at the data row to read from
     * @param columnName column name
     * @return boxed Double value, or null if the value is null or the column doesn't exist.
     */
    public static Double getNullableDouble(Cursor cursor, String columnName) {
        int index = cursor.getColumnIndex(columnName);
        if (index == -1) {
            return null;
        } else {
            return cursor.isNull(index) ? null : cursor.getDouble(index);
        }
    }
}
