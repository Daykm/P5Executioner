package com.daykm.p5executioner.database.di

import android.content.Context
import androidx.room.Room
import com.daykm.p5executioner.database.Dao
import com.daykm.p5executioner.database.P5Database
import dagger.Module
import dagger.Provides
import okio.buffer
import okio.sink
import okio.source

@Module
class DataModule {

    @Provides
    fun database(ctx: Context): P5Database {
        val internalDb = ctx.getDatabasePath("db.sqlite")

        if (internalDb.exists().not()) {
            internalDb.parentFile.mkdirs()
            internalDb.createNewFile()
            val buff = ctx.assets.open("db.db").source()
            val sink = internalDb.sink()

            sink.buffer().writeAll(buff)
        }

        return Room.databaseBuilder(ctx, P5Database::class.java, "db.sqlite")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides
    fun dao(db: P5Database): Dao {
        return db.dao
    }
}