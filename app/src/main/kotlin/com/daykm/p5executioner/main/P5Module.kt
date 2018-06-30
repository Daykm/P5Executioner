package com.daykm.p5executioner.main

import android.app.Activity
import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.fusion.P5FusionAdapter
import com.daykm.p5executioner.info.InfoAdapter
import com.daykm.p5executioner.personas.P5ListAdapter
import com.daykm.p5executioner.skills.P5SkillsAdapter
import com.daykm.p5executioner.view.Pageable
import dagger.Module
import dagger.Provides

@Module
class P5Module(val activity: Activity) {

    @Provides
    fun activity(): Activity = activity

    @Provides fun pool(): RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    @Provides
    fun adapters(fusion: P5FusionAdapter, personas: P5ListAdapter, skills: P5SkillsAdapter, info: InfoAdapter)
            : List<Pageable> = listOf(fusion, personas, skills, info)
}