package com.daykm.p5executioner.personas

import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.R
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.util.color
import com.daykm.p5executioner.view.SimpleSpanBuilder

class PersonaListItemModel(
        private val persona: Persona,
        private val ctx: AppCompatActivity
) : EpoxyModelWithHolder<PersonaListItemHolder>() {

    private val elements: SpannableStringBuilder
    private val stats: String

    init {
        val affinities = persona.affinities.let {
            arrayOf(
                    it.physical to R.color.icon_phys,
                    it.gun to R.color.icon_gun,
                    it.fire to R.color.icon_fire,
                    it.ice to R.color.icon_ice,
                    it.electric to R.color.icon_electric,
                    it.wind to R.color.icon_wind,
                    it.psychic to R.color.icon_psy,
                    it.nuclear to R.color.icon_nuke,
                    it.bless to R.color.icon_bless,
                    it.curse to R.color.icon_curse)
        }

        elements = with(SimpleSpanBuilder()) {
            affinities.forEach {
                append(affinity(it.first), ForegroundColorSpan(ctx.color(it.second)))
            }
            build()
        }

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
            it.view.setOnClickListener {
                /*
                ctx.startActivity(
                        ctx.intentFor<PersonaDetailActivity>("persona" to persona.toByteArray())
                )
                */
            }
        }
    }

    private fun affinity(option: Persona.Affinities.AffinityOption): String = when (option) {
        Persona.Affinities.AffinityOption.NONE -> ctx.getString(R.string.effect_none)
        Persona.Affinities.AffinityOption.WEAK -> ctx.getString(R.string.effect_weak)
        Persona.Affinities.AffinityOption.ABSORB -> ctx.getString(R.string.effect_drain)
        Persona.Affinities.AffinityOption.RESIST -> ctx.getString(R.string.effect_strong)
        Persona.Affinities.AffinityOption.NULL -> ctx.getString(R.string.effect_null)
        Persona.Affinities.AffinityOption.REPEL -> ctx.getString(R.string.effect_repel)
        Persona.Affinities.AffinityOption.UNRECOGNIZED -> throw RuntimeException("Invalid affinity")
    }

    override fun getDefaultLayout(): Int = R.layout.persona_list_item

    override fun createNewHolder(): PersonaListItemHolder = PersonaListItemHolder()

}
