package com.daykm.p5executioner.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

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