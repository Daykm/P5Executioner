package com.daykm.p5executioner.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query

@Dao
interface Dao {
    @Query("SELECT * FROM personas")
    fun personasLiveData(): LiveData<List<Persona>>

    @Query("SELECT * FROM personas")
    fun personas(): List<Persona>

    @Query("SELECT * FROM skills")
    fun skills(): List<Skill>

    @Query("SELECT * FROM skills")
    fun skillsLiveData(): LiveData<List<Skill>>
}