package com.louiscad.splittiessample.extensions.coroutines

import android.arch.persistence.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import splitties.arch.room.DbConfig
import splitties.arch.room.roomDb

inline fun <reified DB : RoomDatabase> roomDataBase(
    name: String,
    crossinline config: DbConfig<DB> = {}
): LazyRoom<DB> =
    LazyRoom(lazy { roomDb(name, config) })

class LazyRoom<T : RoomDatabase> @PublishedApi internal constructor(private val lazyDb: Lazy<T>) {
    suspend operator fun invoke(): T = with(lazyDb) {
        if (isInitialized()) value else withContext(Dispatchers.IO) { value }
    }
}
