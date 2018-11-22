package com.daykm.p5executioner.sandbox.skills.di

import android.support.v7.app.AppCompatActivity
import com.daykm.p5executioner.sandbox.skills.SkillsSandboxActivity
import com.daykm.p5executioner.skills.P5SkillsFragment
import com.daykm.p5executioner.skills.SkillsFragmentComponent
import com.daykm.p5executioner.skills.SkillsModule
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector

@Module(
        subcomponents = [
            SkillsFragmentComponent::class
        ]
)
interface SkillsSandboxActivityModule {
    @Binds
    fun activity(activity: SkillsSandboxActivity): AppCompatActivity

    @ContributesAndroidInjector(modules = [SkillsModule::class])
    fun contributesPersonaListFragment(): P5SkillsFragment

}

@Subcomponent(modules = [SkillsSandboxActivityModule::class])
interface SkillsActivityComponent : AndroidInjector<SkillsSandboxActivity> {

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<SkillsSandboxActivity>()
}
