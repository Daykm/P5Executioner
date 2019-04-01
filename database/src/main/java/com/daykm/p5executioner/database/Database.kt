package com.daykm.p5executioner.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Persona::class, Skill::class], version = 1)
@TypeConverters(Converters::class)
abstract class P5Database : RoomDatabase() {
    abstract val dao: Dao
}
