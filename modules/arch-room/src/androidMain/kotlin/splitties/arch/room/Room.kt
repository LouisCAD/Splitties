/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

package splitties.arch.room

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import splitties.init.appCtx
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

typealias DbConfig<DB> = RoomDatabase.Builder<DB>.() -> Unit

inline fun <reified DB : RoomDatabase> roomDb(
    ctx: Context,
    name: String,
    config: DbConfig<DB> = {}
): DB {
    contract { callsInPlace(config, InvocationKind.EXACTLY_ONCE) }
    return Room.databaseBuilder(ctx, DB::class.java, name).apply(config).build()
}

inline fun <reified DB : RoomDatabase> roomDb(name: String, config: DbConfig<DB> = {}): DB {
    return roomDb(appCtx, name, config)
}

inline fun <DB : RoomDatabase> DB.transaction(body: (db: DB) -> Unit) {
    contract { callsInPlace(body, InvocationKind.EXACTLY_ONCE) }
    beginTransaction()
    try {
        body(this)
        setTransactionSuccessful()
    } finally {
        endTransaction()
    }
}

inline fun <DB : RoomDatabase, R> DB.inTransaction(body: (db: DB) -> R): R {
    contract { callsInPlace(body, InvocationKind.EXACTLY_ONCE) }
    return try {
        beginTransaction()
        body(this).also { setTransactionSuccessful() }
    } finally {
        endTransaction()
    }
}

inline fun RoomDatabase.Builder<*>.onCreate(crossinline block: (db: SupportSQLiteDatabase) -> Unit) {
    addCallback(object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) = block(db)
    })
}

inline fun RoomDatabase.Builder<*>.onOpen(crossinline block: (db: SupportSQLiteDatabase) -> Unit) {
    addCallback(object : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) = block(db)
    })
}
