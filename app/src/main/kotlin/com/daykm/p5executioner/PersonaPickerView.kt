package com.daykm.p5executioner

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.support.v7.widget.RecyclerView.ViewHolder
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.daykm.p5executioner.json.Persona
import dagger.Module
import dagger.Subcomponent
import javax.inject.Inject

class PersonaPickerView : RecyclerView {

  constructor(ctx: Context) : super(ctx)

  constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

  constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs,
      defStyle)

  @Inject @JvmField var pAdapter : PAdapter? = null

  fun inject(component : PersonaComponent) {
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

  val label : TextView = itemView as TextView

  fun bind(persona: Persona) {
    label.text = persona.name
  }
}


@Module class PersonaModule

@Subcomponent(modules = arrayOf(PersonaModule::class)) interface PersonaComponent {

  fun inject(view : PersonaPickerView)

}