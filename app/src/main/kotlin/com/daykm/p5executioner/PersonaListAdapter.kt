package com.daykm.p5executioner

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.LayoutManager
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.databinding.PersonaListItemBinding
import com.daykm.p5executioner.proto.Persona
import com.daykm.p5executioner.proto.Persona.Affinities.AffinityOption
import io.reactivex.functions.Consumer
import javax.inject.Inject

class PersonaListAdapter @Inject constructor(val repo: DataRepo,
    val ctx: Context) : EpoxyController(), Pageable {

  lateinit var personae: List<Persona>

  override fun buildModels() {
    personae.forEach {
      ListModel(it, ctx).id(it.name).addTo(this)
    }
  }

  override fun manager(ctx: Context): LayoutManager {
    val manager = LinearLayoutManager(ctx, LinearLayoutManager.VERTICAL, false)
    manager.recycleChildrenOnDetach = true
    return manager
  }

  override fun attach() {
    repo.DATA.subscribe(Consumer {
      personae = it.personasList
      requestModelBuild()
    })
  }

  override fun adapter(): Adapter<*> {
    return adapter
  }
}

class ListModel(val persona: Persona, val ctx: Context) : EpoxyModelWithHolder<ListItemHolder>() {
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
        append(affinity(it.first, ctx), ForegroundColorSpan(ContextCompat.getColor(ctx, it.second)))
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

  override fun bind(holder: ListItemHolder) {
    holder.binding.let {
      it.personaElements.text = elements
      it.personaStats.text = stats
      it.personaArcana.text = persona.arcana.name
      it.personaName.text = persona.name
      it.personaLevel.text = persona.level.toString()
    }
  }

  fun affinity(option: AffinityOption, ctx: Context): String {
    return when (option) {
      Persona.Affinities.AffinityOption.NONE -> ctx.getString(R.string.effect_none)
      Persona.Affinities.AffinityOption.WEAK -> ctx.getString(R.string.effect_weak)
      Persona.Affinities.AffinityOption.ABSORB -> ctx.getString(R.string.effect_drain)
      Persona.Affinities.AffinityOption.RESIST -> ctx.getString(R.string.effect_strong)
      Persona.Affinities.AffinityOption.NULL -> ctx.getString(R.string.effect_null)
      Persona.Affinities.AffinityOption.REPEL -> ctx.getString(R.string.effect_repel)
      Persona.Affinities.AffinityOption.UNRECOGNIZED -> throw RuntimeException("Invalid affinity")
    }
  }

  override fun getDefaultLayout(): Int {
    return R.layout.persona_list_item
  }

  override fun createNewHolder(): ListItemHolder {
    return ListItemHolder()
  }

}

class ListItemHolder : EpoxyHolder() {
  lateinit var binding: PersonaListItemBinding
  override fun bindView(itemView: View?) {
    binding = PersonaListItemBinding.bind(itemView)
  }
}