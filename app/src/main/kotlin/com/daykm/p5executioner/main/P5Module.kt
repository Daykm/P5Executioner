package com.daykm.p5executioner.main

import android.support.v7.widget.RecyclerView
import com.daykm.p5executioner.fusion.P5FusionAdapter
import com.daykm.p5executioner.info.InfoAdapter
import com.daykm.p5executioner.personas.P5ListAdapter
import com.daykm.p5executioner.skills.P5SkillsAdapter
import com.daykm.p5executioner.view.Pageable
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module class P5Module {
    @Provides fun pool(): RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
    @Provides
    @Named("pageables")
    fun adapters(fusion: P5FusionAdapter, personas: P5ListAdapter, skills: P5SkillsAdapter, info: InfoAdapter)
            : List<Pageable> = listOf(fusion, personas, skills, info)
}