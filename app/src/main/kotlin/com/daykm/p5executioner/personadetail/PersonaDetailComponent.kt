package com.daykm.p5executioner.personadetail

import com.daykm.p5executioner.data.DataRepo
import dagger.Subcomponent


@Subcomponent
interface PersonaDetailComponent {
    fun repo(): DataRepo
}