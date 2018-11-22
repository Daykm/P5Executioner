package com.daykm.p5executioner.sandbox.fusion.di

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.fusion.FusionFragment
import com.daykm.p5executioner.fusion.FusionFragmentComponent
import com.daykm.p5executioner.fusion.FusionModule
import com.daykm.p5executioner.sandbox.LoggingRecyclerPool
import com.daykm.p5executioner.sandbox.fusion.FusionSandboxActivity
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module(
        subcomponents = [
            FusionFragmentComponent::class
        ]
)
interface FusionSandboxActivityModule {

    @Binds
    fun activity(activity: FusionSandboxActivity): AppCompatActivity

    @Binds
    fun pool(pool: LoggingRecyclerPool): RecyclerView.RecycledViewPool

    @ContributesAndroidInjector(modules = [FusionModule::class])
    fun contributesPersonaListFragment(): FusionFragment

}

@Subcomponent(modules = [FusionSandboxActivityModule::class])
interface FusionActivityComponent : AndroidInjector<FusionSandboxActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<FusionSandboxActivity>()
}
