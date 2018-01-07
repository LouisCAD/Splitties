/*
 * Copyright (c) 2018. Louis Cognault Ayeva Derman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package splitties.arch.room

import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import splitties.init.appCtx

typealias DbConfig<DB> = RoomDatabase.Builder<DB>.() -> Unit

inline fun <reified DB : RoomDatabase> roomDb(name: String, config: DbConfig<DB> = {}): DB {
    return Room.databaseBuilder(appCtx, DB::class.java, name).apply(config).build()
}

inline fun <DB : RoomDatabase> DB.transaction(body: (db: DB) -> Unit) {
    beginTransaction()
    try {
        body(this)
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}
