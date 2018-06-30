package com.daykm.p5executioner.personadetail

import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.daykm.p5executioner.App
import com.daykm.p5executioner.R
import com.daykm.p5executioner.data.skillsForPersona
import com.daykm.p5executioner.proto.Persona
import io.reactivex.rxkotlin.subscribeBy


class PersonaDetailActivity : AppCompatActivity() {

    @BindView(R.id.detail_level)
    lateinit var level: TextView
    @BindView(R.id.detail_arcana)
    lateinit var arcana: TextView
    @BindView(R.id.detail_toolbar)
    lateinit var toolbar: CollapsingToolbarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person_detail)
        ButterKnife.bind(this)

        App.INSTANCE.component.personaDetail().repo().DATA.subscribeBy { data ->
            val persona = unbundle(intent.extras)

            level.text = persona.level.toString()
            arcana.text = persona.arcana.name
            toolbar.title = persona.name

            val skills = data.skillsForPersona(persona)
        }
    }
}

fun bundleArgs(persona: Persona) = Bundle().putByteArray("persona", persona.toByteArray())
fun unbundle(bundle: Bundle): Persona = Persona.parseFrom(bundle.getByteArray("persona"))