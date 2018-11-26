package com.daykm.p5executioner.sandbox.skills.di

import android.support.v7.app.AppCompatActivity
import com.daykm.p5executioner.sandbox.skills.SkillsSandboxActivity
import com.daykm.p5executioner.skills.di.MatronSkillsModule
import com.daykm.p5executioner.skills.di.SkillsComponent
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module(
        includes = [
            MatronSkillsModule::class
        ],
        subcomponents = [
            SkillsComponent::class
        ]
)
interface SkillsSandboxActivityModule {
    @Binds
    fun activity(activity: SkillsSandboxActivity): AppCompatActivity

}

@Subcomponent(modules = [SkillsSandboxActivityModule::class])
interface SkillsActivityComponent : AndroidInjector<SkillsSandboxActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SkillsSandboxActivity>()
}
