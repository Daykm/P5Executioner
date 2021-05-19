package com.daykm.p5executioner.skills

import androidx.lifecycle.ViewModel
import com.daykm.p5executioner.database.Dao
import timber.log.Timber
import javax.inject.Inject

class SkillsViewModel
@Inject constructor(
        private val dao: Dao
) : ViewModel() {

    init {
        Timber.i("Creating SkillsViewModel")
    }

    val skills = dao.skillsLiveData()
}