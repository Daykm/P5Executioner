package com.daykm.p5executioner.main

import android.app.Activity
import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.di.ActivityScope
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(P5Module::class)])
interface P5Component {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun activity(activity: Activity)

        @BindsInstance
        fun pool(pool: RecyclerView.RecycledViewPool)

        fun build(): P5Component
    }
}