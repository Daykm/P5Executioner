package com.daykm.p5executioner.personas

import android.content.Context
import android.content.res.ColorStateList
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.database.Affinity
import com.daykm.p5executioner.database.Persona
import com.daykm.p5executioner.personas.databinding.PersonaListItemBinding

class PersonaListItemModel(
        val persona: Persona,
        private val ctx: AppCompatActivity
) : EpoxyModelWithHolder<PersonaListItemHolder>() {

    val elements: SpannableStringBuilder
    val stats: String

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
        holder.bindModel(this)
    }

    private fun affinity(option: Affinity): String = when (option) {
        Affinity.NONE -> ctx.getString(R.string.effect_none)
        Affinity.WEAK -> ctx.getString(R.string.effect_weak)
        Affinity.ABSORB -> ctx.getString(R.string.effect_drain)
        Affinity.RESIST -> ctx.getString(R.string.effect_strong)
        Affinity.NULL -> ctx.getString(R.string.effect_null)
        Affinity.REPEL -> ctx.getString(R.string.effect_repel)
    }

    override fun getDefaultLayout(): Int = R.layout.persona_list_item

    override fun createNewHolder(): PersonaListItemHolder = PersonaListItemHolder()

}


class PersonaListItemHolder : EpoxyHolder() {
    private lateinit var binding: PersonaListItemBinding

    override fun bindView(itemView: View) {
        binding = PersonaListItemBinding.bind(itemView)
    }

    fun bindModel(model: PersonaListItemModel) {
        binding.personaElements.text = model.elements
        binding.personaStats.text = model.stats
        binding.personaArcana.text = model.persona.arcana.name
        binding.personaName.text = model.persona.name
        binding.personaLevel.text = model.persona.level.toString()
        binding.personaItem.setOnClickListener {
            /*
            ctx.startActivity(
                    ctx.intentFor<PersonaDetailActivity>("persona" to persona.toByteArray())
            )
            */
        }
    }
}

private fun Context.colorStateList(@ColorRes color: Int): ColorStateList = ContextCompat.getColorStateList(this, color)!!
private fun Context.color(@ColorRes color: Int): Int = ContextCompat.getColor(this, color)
