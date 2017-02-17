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

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import com.actinarium.nagbox.database.NagboxContract.TasksTable;

/**
 * A content provider for Nagbox app database, configured to only work for querying data
 *
 * @author Paul Danyliuk
 */
public class NagboxContentProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private static final int ALL_TASKS = 0;

    private NagboxDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = NagboxDbHelper.getInstance(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case ALL_TASKS:
                return TasksTable.CONTENT_TYPE_DIR;
            default:
                throw new IllegalArgumentException("Cannot resolve given URI: " + uri);
        }
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (sUriMatcher.match(uri)) {
            case ALL_TASKS:
                final Cursor cursor = mDbHelper.getReadableDatabase().query(
                        TasksTable.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder
                );
                //noinspection ConstantConditions
                cursor.setNotificationUri(getContext().getContentResolver(), TasksTable.CONTENT_URI);
                return cursor;
            default:
                throw new IllegalArgumentException("Cannot resolve given URI: " + uri);
        }
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        throw new IllegalArgumentException("Insert operations not supported - use DbOps");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        throw new IllegalArgumentException("Delete operations not supported - use DbOps");
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new IllegalArgumentException("Update operations not supported - use DbOps");
    }

    private static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(
                NagboxContract.CONTENT_AUTHORITY,
                NagboxContract.PATH_TASKS,
                ALL_TASKS
        );
        return uriMatcher;
    }
}
