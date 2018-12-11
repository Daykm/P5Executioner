package com.daykm.p5executioner.sandbox.fusion.di

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.fusion.di.MatronFusionModule
import com.daykm.p5executioner.sandbox.LoggingRecyclerPool
import com.daykm.p5executioner.sandbox.fusion.FusionSandboxActivity
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module(
        includes = [
            MatronFusionModule::class
        ]
)
interface FusionSandboxActivityModule {

    @Binds
    fun activity(activity: FusionSandboxActivity): AppCompatActivity

    @Binds
    fun pool(pool: LoggingRecyclerPool): RecyclerView.RecycledViewPool

}

@Subcomponent(modules = [FusionSandboxActivityModule::class])
interface FusionActivityComponent : AndroidInjector<FusionSandboxActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FusionSandboxActivity>()
}
