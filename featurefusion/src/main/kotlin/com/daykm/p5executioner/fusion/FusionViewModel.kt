package com.daykm.p5executioner.fusion

import android.arch.lifecycle.ViewModel
import com.daykm.p5executioner.database.Dao
import timber.log.Timber
import javax.inject.Inject

class FusionViewModel
@Inject constructor(
        private val dao: Dao
) : ViewModel() {

    init {
        Timber.i("Creating FusionViewModel")
    }

    val firstPersonas = dao.personasLiveData()
    val secondPersonas = dao.personasLiveData()
}