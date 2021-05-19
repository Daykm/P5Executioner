package com.daykm.p5executioner.sandbox

import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import javax.inject.Inject

class LoggingRecyclerPool
@Inject constructor() : RecyclerView.RecycledViewPool() {
    init {
        Timber.i("Creating instance")
    }
}
