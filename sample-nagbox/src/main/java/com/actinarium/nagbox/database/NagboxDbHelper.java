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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.RawRes;
import android.text.TextUtils;
import android.util.Log;
import com.actinarium.nagbox.R;
import com.actinarium.nagbox.common.ReaderUtils;
import com.actinarium.nagbox.model.Task;

/**
 * A standard DB open helper class, as per Udacity course / Android docs. Singleton.
 *
 * @author Paul Danyliuk
 */
public class NagboxDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "NagboxDbHelper";

    private static final String DATABASE_NAME = "nagbox.db";
    private static final int DATABASE_VERSION = 3;

    private static NagboxDbHelper sInstance;

    private final Context mContext;

    private NagboxDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static synchronized NagboxDbHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new NagboxDbHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // When a few migrations have accumulated, it makes sense to update the base schema.
        // Like, now we're shipping with v3
        execFile(db, R.raw.schema_v3);
        importInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int from, int to) {
        // You can use switch statement on 'from' without breaks for fall-through
        switch (from) {
            case 1:
                Log.i(TAG, "Migrating DB: v1 -> v2");
                execFile(db, R.raw.migration_v1_v2);
            case 2:
                Log.i(TAG, "Migrating DB: v2 -> v3");
                execFile(db, R.raw.migration_v2_v3);
//          case 3:
//              Log.i(TAG, "Migrating DB: v3 -> v4");
//              execFile(db, R.raw.migration_v3_v4);
//          ...etc
        }
    }

    /**
     * Execute all SQL instructions from the specified text file. The instructions must be separated by semicolons (;)
     *
     * @param db         Database instance
     * @param sqlFileRes ID of the file with SQL queries placed in /res/raw
     */
    private void execFile(SQLiteDatabase db, @RawRes int sqlFileRes) {
        final String[] queries = TextUtils.join(" ", ReaderUtils.readLines(mContext, sqlFileRes)).split(";");
        for (String query : queries) {
            db.execSQL(query);
        }
    }

    /**
     * Import starter data into the database
     *
     * @param db Database instance
     */
    private void importInitialData(SQLiteDatabase db) {
        // Five starter tasks
        final int length = 5;
        final String[] starterTaskTitles = mContext.getResources().getStringArray(R.array.starter_tasks);
        final int[] starterTaskIntervals = {2, 5, 5, 5, 3};

        NagboxDbOps.Transaction transaction = NagboxDbOps.startTransaction(db);
        Task reusableTask = new Task();
        for (int i = 0; i < length; i++) {
            reusableTask.title = starterTaskTitles[i];
            reusableTask.interval = starterTaskIntervals[i];
            reusableTask.displayOrder = i + 1;
            transaction.createTask(reusableTask);
        }
        transaction.commit();
    }

}
