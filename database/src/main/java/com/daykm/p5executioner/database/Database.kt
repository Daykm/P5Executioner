package com.daykm.p5executioner.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import okio.buffer
import okio.sink
import okio.source

@Database(entities = [Persona::class, Skill::class], version = 1)
@TypeConverters(Converters::class)
abstract class P5Database : RoomDatabase() {
    abstract val dao: Dao
}

fun init(ctx: Context): P5Database {

    val internalDb = ctx.getDatabasePath("db.sqlite")

    if (internalDb.exists().not()) {
        val buff = ctx.assets.open("db.db").source()
        val sink = internalDb.sink()

        sink.buffer().writeAll(buff)
    }


    return Room.databaseBuilder(ctx, P5Database::class.java, "db.sqlite")
            .fallbackToDestructiveMigration()
            .build()
}