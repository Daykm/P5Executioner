package com.daykm.p5executioner.fusion

import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Arcana
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.proto.SpecialCombo
import javax.inject.Inject


class FusionCalculator
@Inject constructor(
        val data: DataRepo
) {

    lateinit var specialCombos: List<SpecialCombo>
    lateinit var personaByArcana: MutableMap<Arcana, MutableList<Persona>>

    init {
        data.DATA.blockingGet().apply {
            specialCombos = specialCombosList

            personaByArcana = HashMap()
            Arcana.values().forEach {
                personaByArcana.put(it, ArrayList())
            }
            personasList.forEach {
                personaByArcana[it.arcana]!!.add(it)
            }

            personaByArcana.values.forEach {
                it.sortBy {
                    it.level
                }
                it.reverse()
            }
        }
    }

    fun fuse(first: Persona, second: Persona) {

    }

    fun sameArcanaFusion(arcana: Arcana, level: Int, first: Persona, second: Persona): Persona? {
        personaByArcana[arcana]!!.filter {
            first != it && second != it
        }

        personaByArcana[arcana]!!.forEach {
            if (it.level <= level && first != it && second != it) {
                return it
            }
        }
        return null
    }


    fun getSpecialFuse(first: Persona, second: Persona): SpecialCombo? =
            specialCombos.filter {
                it.sourcesList.containsAll(listOf(first.name, second.name))
            }.firstOrNull()

}
