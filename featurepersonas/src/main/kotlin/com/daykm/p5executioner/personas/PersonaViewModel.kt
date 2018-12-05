package com.daykm.p5executioner.personas

import android.arch.lifecycle.ViewModel
import com.daykm.p5executioner.database.Dao
import javax.inject.Inject

class PersonaViewModel
@Inject constructor(
        private val dao: Dao
) : ViewModel() {

    val personas = dao.personasLiveData()
}