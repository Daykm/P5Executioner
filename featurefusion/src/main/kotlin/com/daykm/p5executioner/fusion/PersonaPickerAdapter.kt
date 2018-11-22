package com.daykm.p5executioner.fusion

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.View
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.database.Persona
import com.daykm.p5executioner.fusion.databinding.PersonaCardBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber

class PersonaPickerAdapter : TypedEpoxyController<List<Persona>>() {

    private val selectedPersona: BehaviorSubject<Persona> = BehaviorSubject.create()

    override fun buildModels(data: List<Persona>?) {
        Timber.i("Building %d models", data?.size ?: 0)
        data?.forEach { PersonaModel(it, selectedPersona).addTo(this) }
    }

}

data class PersonaModel(
        val persona: Persona,
        private val subject: BehaviorSubject<Persona>
) : EpoxyModelWithHolder<PersonaHolder>() {

    var selected = false
        private set

    private val disposable = CompositeDisposable()

    init {
        disposable.add(subject.subscribe { selected = it == persona })
        id(persona.name + persona.arcana + selected.toString())
    }

    override fun createNewHolder(): PersonaHolder = PersonaHolder()

    override fun getDefaultLayout(): Int = R.layout.persona_card

    override fun bind(holder: PersonaHolder) {
        holder.bindModel(this)
    }

    override fun unbind(holder: PersonaHolder) = disposable.clear()

    fun notifyClick() {
        subject.onNext(persona)
    }
}

class PersonaHolder : EpoxyHolder() {

    private lateinit var binding: PersonaCardBinding

    override fun bindView(itemView: View) {
        binding = PersonaCardBinding.bind(itemView)
    }

    fun bindModel(model: PersonaModel) {
        binding.cardName.text = model.persona.name
        binding.cardLevel.text = model.persona.level.toString()
        binding.cardArcana.text = model.persona.arcana.name
        if (model.selected) {
            val ctx = binding.root.context
            binding.cardPersona.setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorPrimaryDark))
        } else {
            binding.cardPersona.setBackgroundColor(Color.TRANSPARENT)
        }
        binding.cardPersona.setOnClickListener { model.notifyClick() }
    }

}
