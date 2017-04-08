package com.daykm.p5executioner

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

class PersonaPickerView : RecyclerView {

  constructor(ctx: Context) : super(ctx)

  constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

  constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,
      defStyle)

  @Inject @JvmField var pAdapter: PAdapter? = null

  fun inject(component: PersonaComponent) {
    component.inject(this)
    // inject here
    adapter = pAdapter
  }

  init {
    val linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
    linearLayoutManager.recycleChildrenOnDetach = true
    layoutManager = linearLayoutManager
  }
}

class VH(parent: ViewGroup) : ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.persona_item, parent, false)
) {

  val name: TextView = itemView.findViewById(R.id.name) as TextView
  val level: TextView = itemView.findViewById(R.id.level) as TextView
  val arcana: TextView = itemView.findViewById(R.id.arcana) as TextView

  fun bind(persona: Persona) {
    name.text = persona.name
    level.text = persona.level.toString()
    arcana.text = persona.arcana.label
  }
}

@Module class PersonaModule

@Subcomponent(modules = arrayOf(PersonaModule::class)) interface PersonaComponent {

  fun inject(view: PersonaPickerView)

}