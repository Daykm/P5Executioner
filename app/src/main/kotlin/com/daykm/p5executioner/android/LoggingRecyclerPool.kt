package com.daykm.p5executioner.android

import android.support.v7.widget.RecyclerView
import timber.log.Timber
import javax.inject.Inject

class LoggingRecyclerPool
@Inject constructor() : RecyclerView.RecycledViewPool() {
    init {
        Timber.i("Creating instance")
    }
}
