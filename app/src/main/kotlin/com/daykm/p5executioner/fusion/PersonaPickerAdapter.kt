package com.daykm.p5executioner.fusion

import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.TypedEpoxyController
import com.daykm.p5executioner.R
import com.daykm.p5executioner.data.DataRepo
import com.daykm.p5executioner.proto.Persona
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.BehaviorSubject
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject

class PersonaPickerAdapter @Inject constructor(val repo: DataRepo) : TypedEpoxyController<List<Persona>>() {

    val selectedPersona: BehaviorSubject<Persona> = BehaviorSubject.create()

    override fun buildModels(data: List<Persona>?) {
        Timber.i("Building %d models", data?.size ?: 0)
        data?.forEach { PersonaModel(it, selectedPersona).addTo(this) }
    }

    override fun onExceptionSwallowed(exception: RuntimeException?) = Timber.e(exception)

    fun bind() {
        repo.DATA.observeOn(AndroidSchedulers.mainThread()).subscribeBy { setData(it.personasList) }
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

    override fun createNewHolder(): PersonaHolder = PersonaHolder()

    override fun getDefaultLayout(): Int = R.layout.persona_card

    override fun bind(holder: PersonaHolder) = holder.let {
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

    override fun unbind(holder: PersonaHolder?) = disposable.clear()
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
