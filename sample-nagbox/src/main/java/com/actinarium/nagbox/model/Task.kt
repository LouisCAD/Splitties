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

package com.actinarium.nagbox.model

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import com.actinarium.nagbox.database.NagboxContract
import com.actinarium.nagbox.model.Task.Companion.FLAG_ACTIVE
import com.actinarium.nagbox.model.Task.Companion.FLAG_NOT_SEEN
import xyz.louiscad.nagbox.createParcel

/**
 * An entity object for a task

 * @author Paul Danyliuk
 */
data class Task(
        var id: Long = NO_ID,
        var title: String = "",
        /** Interval in minutes */
        var interval: Int = DEFAULT_INTERVAL,
        /**
         * Holds status flags for this task
         * @see FLAG_ACTIVE
         * @see FLAG_NOT_SEEN
         */
        var flags: Int = 0,
        /**
         * Timestamp (msec) when this task must fire next time. Holds actual value only when [.isActive] is
         * `true`, otherwise it can be anything.
         */
        var nextFireAt: Long = 0L,
        /**
         * Timestamp (msec) when this task was last started. Can be undefined (usually 0) until started at least once.
         */
        var lastStartedAt: Long = 0L,
        /**
         * Position of this task in the list. The app should make all efforts to ensure it's unique, but it doesn't have to
         * be continuous (i.e. no need to recalculate order when deleting items). Will be used for drag-to-reorder.
         */
        var displayOrder: Int = 0
) : Parcelable {

    fun copy() = this.copy(id = id)
    // Getters/setters for 2-way data binding ----------------------------

    // Getters/setters for convenient flag setting -----------------------

    var isActive: Boolean
        get() = flags and FLAG_ACTIVE != 0
        set(isActive) = if (isActive) {
            flags = flags or FLAG_ACTIVE
        } else {
            flags = flags and FLAG_ACTIVE.inv()
        }

    var isSeen: Boolean
        get() = flags and FLAG_NOT_SEEN == 0
        set(isSeen) = if (isSeen) {
            flags = flags and FLAG_NOT_SEEN.inv()
        } else {
            flags = flags or FLAG_NOT_SEEN
        }

    var intervalAsString: String
        get() = Integer.toString(interval)
        set(value) {
            interval = if (value.isEmpty()) 0 else Integer.parseInt(value)
        }

    // Export into ContentValues for insert/update ops -------------------

    /**
     * Get [ContentValues] for this model to feed it to create/restore operations

     * @return `ContentValues` with all fields
     * *
     * @see toContentValuesOnStatusChange()
     * @see toContentValuesOnUpdate()
     */
    fun toContentValues() = ContentValues(6).apply {
        put(NagboxContract.TasksTable.COL_TITLE, title)
        put(NagboxContract.TasksTable.COL_INTERVAL, interval)
        put(NagboxContract.TasksTable.COL_FLAGS, flags)
        put(NagboxContract.TasksTable.COL_NEXT_FIRE_AT, nextFireAt)
        put(NagboxContract.TasksTable.COL_LAST_STARTED_AT, lastStartedAt)
        put(NagboxContract.TasksTable.COL_DISPLAY_ORDER, displayOrder)
    }

    /**
     * Get [ContentValues] for this model to feed it to update description operations

     * @return `ContentValues` with title and interval
     */
    fun toContentValuesOnUpdate() = ContentValues(2).apply {
        put(NagboxContract.TasksTable.COL_TITLE, title)
        put(NagboxContract.TasksTable.COL_INTERVAL, interval)
    }

    /**
     * Get [ContentValues] for this model when its status (flags, last started and next fire time) needs to be
     * updated

     * @return `ContentValues` with flags, lastStartedAt, and nextFireAt
     */
    fun toContentValuesOnStatusChange()= ContentValues(3).apply {
        put(NagboxContract.TasksTable.COL_FLAGS, flags)
        put(NagboxContract.TasksTable.COL_LAST_STARTED_AT, lastStartedAt)
        put(NagboxContract.TasksTable.COL_NEXT_FIRE_AT, nextFireAt)
    }

    protected constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readLong(),
            source.readLong(),
            source.readInt()
    )

    override fun describeContents() = 0
    override fun writeToParcel(dest: Parcel, flags: Int) = dest.run {
        writeLong(id)
        writeString(title)
        writeInt(interval)
        writeInt(flags)
        writeLong(nextFireAt)
        writeLong(lastStartedAt)
        writeInt(displayOrder)
        Unit
    }

    companion object {

        /**
         * Indicates that this task is active, and alarm must be scheduled for this task
         */
        val FLAG_ACTIVE = 1
        /**
         * Indicates that this task already fired and was rescheduled, but the user haven't dismissed the notification yet,
         * so it should be included upon the next alarm.
         */
        val FLAG_NOT_SEEN = 2

        val DEFAULT_INTERVAL = 5
        val NO_ID = -1L

        @JvmField val CREATOR = createParcel(::Task)
    }
}
