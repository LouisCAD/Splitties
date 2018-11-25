package com.louiscad.splittiessample.extensions.coroutines

import android.arch.persistence.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import splitties.arch.room.DbConfig
import splitties.arch.room.roomDb

inline fun <reified DB : RoomDatabase> roomDataBase(
    name: String,
    crossinline config: DbConfig<DB> = {}
): SuspendLazy<DB> = SuspendLazy(Dispatchers.IO) { roomDb(name, config) }
