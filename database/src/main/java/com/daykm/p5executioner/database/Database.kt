package com.daykm.p5executioner.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters

@Database(entities = [Persona::class, Skill::class], version = 1)
@TypeConverters(Converters::class)
abstract class P5Database : RoomDatabase() {
    abstract val dao: Dao
}
