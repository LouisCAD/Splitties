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
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

/**
 * An interface for objects that handle {@link Cursor} -&gt; model object mapping. One <code>Projection</code> object
 * encapsulates the list of columns that should be queried, and the logic to properly map those columns to the model's
 * fields.
 *
 * @author Paul Danyliuk
 */
public interface Projection<T> {

    /**
     * Get projection columns, i.e. which ones to query from the database
     *
     * @return the array of column names or column expressions (e.g. <code>"user.id AS user_id"</code>) to pass to SQL
     * <code>SELECT</code> statement (or, actually, {@link SQLiteDatabase#query(String, String[], String, String[],
     * String, String, String)}).
     */
    String[] getColumns();

    /**
     * Convert current cursor row, queried with this projection, into a model object. This method should fill in
     * existing model if possible, or create a new one otherwise.
     *
     * @param cursor Cursor to read data from. Must be already positioned on required row.
     * @param model  Model to fill from this cursor, if it's mutable. If the model is null, this method must create
     *               and return a new model object.
     * @return model object mapped from the provided cursor.
     */
    T mapCursorToModel(Cursor cursor, @Nullable T model);

    /**
     * Get row ID at current cursor position
     * @param cursor Cursor to read data from. Must be already positioned on required row.
     * @return row ID or {@link RecyclerView#NO_ID} if current projection doesn't include IDs
     */
    long getId(Cursor cursor);
}
