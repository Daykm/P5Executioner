package com.daykm.p5executioner.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM personas")
    fun personas(): List<Persona>
}