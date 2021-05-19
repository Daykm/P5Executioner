package com.daykm.p5executioner.sandbox.skills.di

import androidx.appcompat.app.AppCompatActivity
import com.daykm.p5executioner.sandbox.skills.SkillsSandboxActivity
import com.daykm.p5executioner.skills.di.MatronSkillsModule
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Module(
        includes = [
            MatronSkillsModule::class
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
