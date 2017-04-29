package com.daykm.p5executioner.fusion

import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.daykm.p5executioner.R
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Data
import com.daykm.p5executioner.proto.Persona
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
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
        return PersonaHolder()
    }

    override fun getDefaultLayout(): Int {
        return R.layout.persona_card
    }

    override fun bind(holder: PersonaHolder) {
        holder.let {
            it.name.text = persona.name
            it.level.text = persona.level.toString()
            it.arcana.text = persona.arcana.name
            if (selected) {
                it.card.setBackgroundColor(
                        ContextCompat.getColor(it.card.context, R.color.colorPrimaryDark))
            } else {
                it.card.setBackgroundColor(Color.TRANSPARENT)
            }
            it.card.setOnClickListener { subject.onNext(persona) }
        }
    }

    fun destroy() {
        disposable.dispose()
    }
}

class PersonaHolder : EpoxyHolder() {
    @BindView(R.id.card_name)
    lateinit var name: TextView
    @BindView(R.id.card_arcana)
    lateinit var arcana: TextView
    @BindView(R.id.card_level)
    lateinit var level: TextView
    @BindView(R.id.card_persona)
    lateinit var card: ConstraintLayout

    override fun bindView(itemView: View) {
        ButterKnife.bind(this, itemView)
    }
}
