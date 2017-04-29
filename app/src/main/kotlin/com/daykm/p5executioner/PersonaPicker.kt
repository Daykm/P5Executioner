package com.daykm.p5executioner

import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.view.View
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.databinding.PersonaCardBinding
import com.daykm.p5executioner.proto.Data
import com.daykm.p5executioner.proto.Persona
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.jetbrains.anko.onClick
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject

class PersonaPickerAdapter @Inject constructor(val repo: DataRepo) : EpoxyController() {

    val selectedPersona: BehaviorSubject<Persona> = BehaviorSubject.create()
    var personas: List<Persona>? = null
    var personaModels: MutableList<PersonaModel>? = null

    override fun buildModels() {
        Timber.i("Building model")
        personaModels?.forEach { it.destroy() }
        personaModels?.clear()
        personas?.forEach { persona ->
            run {
                val model = PersonaModel(persona, selectedPersona)
                personaModels?.add(model)
                model.addTo(this)
            }
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
        repo.DATA.observeOn(AndroidSchedulers.mainThread()).subscribe { data: Data ->
            run {
                personas = data.personasList
                requestModelBuild()
            }
        }
    }
}

data class PersonaModel(val persona: Persona, val subject: BehaviorSubject<Persona>)
    : EpoxyModelWithHolder<PersonaHolder>() {

    var selected = false

    val disposable = CompositeDisposable()

    init {
        disposable.add(subject.subscribe { selected = it == persona })
        id(persona.name + persona.arcana + selected.toString())
    }

    override fun createNewHolder(): PersonaHolder {
        return PersonaHolder(subject)
    }

    override fun getDefaultLayout(): Int {
        return R.layout.persona_card
    }

    override fun bind(holder: PersonaHolder) {
        holder.bind(this)
    }

    fun destroy() {
        disposable.dispose()
    }
}

class PersonaHolder(val subject: BehaviorSubject<Persona>) : EpoxyHolder() {

    lateinit var binding: PersonaCardBinding
    lateinit var defaultBackground: ColorStateList

    fun bind(model: PersonaModel) {
        binding.name.text = model.persona.name
        binding.level.text = model.persona.level.toString()
        binding.arcana.text = model.persona.arcana.name
        if (model.selected) {
            binding.persona.setCardBackgroundColor(
                    ContextCompat.getColor(binding.root.context, R.color.colorPrimaryDark))
        } else {
            binding.persona.cardBackgroundColor = defaultBackground
        }
        binding.persona.onClick { subject.onNext(model.persona) }
    }

    override fun bindView(itemView: View?) {
        binding = PersonaCardBinding.bind(itemView)
        defaultBackground = binding.persona.cardBackgroundColor
    }
}
