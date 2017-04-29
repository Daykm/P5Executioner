package com.daykm.p5executioner.personas

import android.content.Context
import android.support.v4.content.ContextCompat
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.R
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.util.SimpleSpanBuilder

class PersonaListItemModel(val persona: Persona, val ctx: Context) : EpoxyModelWithHolder<PersonaListItemHolder>() {
    val elements: SpannableStringBuilder
    val stats: String

    init {
        elements = SimpleSpanBuilder().apply {
            persona.affinities.let {
                arrayOf(
                        Pair(it.physical, R.color.icon_phys),
                        Pair(it.gun, R.color.icon_gun),
                        Pair(it.fire, R.color.icon_fire),
                        Pair(it.ice, R.color.icon_ice),
                        Pair(it.electric, R.color.icon_electric),
                        Pair(it.wind, R.color.icon_wind),
                        Pair(it.psychic, R.color.icon_psy),
                        Pair(it.nuclear, R.color.icon_nuke),
                        Pair(it.bless, R.color.icon_bless),
                        Pair(it.curse, R.color.icon_curse))
            }.forEach {
                append(affinity(it.first), ForegroundColorSpan(ContextCompat.getColor(ctx, it.second)))
            }
        }.build()

        stats = persona.stats.let {
            ctx.resources.getStringArray(R.array.persona_stats).zip(
                    arrayOf(
                            it.strength,
                            it.magic,
                            it.endurance,
                            it.agility,
                            it.luck)
            ).map {
                String.format("%s %02d", it.first, it.second)
            }.joinToString(" ")
        }
    }

    override fun bind(holder: PersonaListItemHolder) {
        holder.let {
            it.elems.text = elements
            it.stats.text = stats
            it.arcana.text = persona.arcana.name
            it.name.text = persona.name
            it.level.text = persona.level.toString()
        }
    }

    fun affinity(option: Persona.Affinities.AffinityOption): String = when (option) {
        Persona.Affinities.AffinityOption.NONE -> ctx.getString(R.string.effect_none)
        Persona.Affinities.AffinityOption.WEAK -> ctx.getString(R.string.effect_weak)
        Persona.Affinities.AffinityOption.ABSORB -> ctx.getString(R.string.effect_drain)
        Persona.Affinities.AffinityOption.RESIST -> ctx.getString(R.string.effect_strong)
        Persona.Affinities.AffinityOption.NULL -> ctx.getString(R.string.effect_null)
        Persona.Affinities.AffinityOption.REPEL -> ctx.getString(R.string.effect_repel)
        Persona.Affinities.AffinityOption.UNRECOGNIZED -> throw RuntimeException("Invalid affinity")
    }

    override fun getDefaultLayout(): Int {
        return R.layout.persona_list_item
    }

    override fun createNewHolder(): PersonaListItemHolder {
        return PersonaListItemHolder()
    }

}