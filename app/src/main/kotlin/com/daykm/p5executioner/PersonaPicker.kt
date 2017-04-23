package com.daykm.p5executioner

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.databinding.PersonaItemBinding
import io.reactivex.subjects.BehaviorSubject
import org.jetbrains.anko.onClick
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject

class PersonaPickerAdapter @Inject constructor(val repo: DataRepo) : EpoxyController() {

  val selectedPersona: BehaviorSubject<Persona> = BehaviorSubject.create()
  var personas: List<Persona>? = null

  override fun buildModels() {
    Timber.i("Building model")
    personas?.forEach { persona ->
      PersonaModel(persona, selectedPersona).addTo(this)
    }
  }

  init {
    setFilterDuplicates(true)
    selectedPersona.subscribe { requestModelBuild() }
  }

  override fun onExceptionSwallowed(exception: RuntimeException?) {
    Timber.e(exception)
  }

  fun bind() {
    repo.PERSONAE.subscribe { list ->
      run {
        personas = list
        requestModelBuild()
      }
    }
  }
}

data class PersonaModel(val persona: Persona, val subject: BehaviorSubject<Persona>)
  : EpoxyModelWithHolder<PersonaHolder>() {

  var selected = false

  init {
    subject.subscribe { selected = it == persona }
    id(persona.name + persona.arcana + selected.toString())
  }

  override fun createNewHolder(): PersonaHolder {
    return PersonaHolder(subject)
  }

  override fun getDefaultLayout(): Int {
    return R.layout.persona_item
  }

  override fun bind(holder: PersonaHolder) {
    holder.bind(this)
  }
}

class PersonaHolder(val subject: BehaviorSubject<Persona>) : EpoxyHolder() {

  lateinit var binding: PersonaItemBinding
  lateinit var defaultBackground: ColorStateList

  fun bind(model: PersonaModel) {
    binding.name.text = model.persona.name
    binding.level.text = model.persona.level.toString()
    binding.arcana.text = model.persona.arcana.label
    if (model.selected) {
      binding.persona.setCardBackgroundColor(
          ContextCompat.getColor(binding.root.context, R.color.colorPrimaryDark))
    } else {
      binding.persona.cardBackgroundColor = defaultBackground
    }
    binding.persona.onClick { subject.onNext(model.persona) }
  }

  override fun bindView(itemView: View?) {
    binding = PersonaItemBinding.bind(itemView)
    defaultBackground = binding.persona.cardBackgroundColor
  }
}
