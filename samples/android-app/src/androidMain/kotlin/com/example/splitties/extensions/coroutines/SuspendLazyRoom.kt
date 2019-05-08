/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package com.example.splitties.extensions.coroutines

import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import splitties.arch.room.DbConfig
import splitties.arch.room.roomDb

inline fun <reified DB : RoomDatabase> roomDataBase(
    name: String,
    crossinline config: DbConfig<DB> = {}
): SuspendLazy<DB> = SuspendLazy(Dispatchers.IO) { roomDb(name, config) }
