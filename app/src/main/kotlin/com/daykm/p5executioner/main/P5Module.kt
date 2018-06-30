package com.daykm.p5executioner.main

import android.app.Activity
import android.support.v7.widget.RecyclerView
import dagger.Module
import dagger.Provides

@Module
class P5Module(private val activity: Activity) {

    @Provides
    fun activity(): Activity = activity

    @Provides fun pool(): RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()

}