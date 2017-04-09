package com.daykm.p5executioner

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Scope

class FusionByComponentsView : RecyclerView {

	constructor(ctx: Context) : super(ctx)

	constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

	constructor(context: Context?, attrs: AttributeSet?, defStyle: Int)
			: super(context, attrs, defStyle)


	@Inject lateinit var componentAdapter: ComponentsAdapter

	val firstPersona: BehaviorSubject<Persona> = BehaviorSubject.create()
	val secondPersona: BehaviorSubject<Persona> = BehaviorSubject.create()

	init {
		App.INSTANCE.component.component().inject(this)
		adapter = componentAdapter

	}

	override fun onSaveInstanceState(): Parcelable {
		return
	}

	override fun onRestoreInstanceState(state: Parcelable?) {
		super.onRestoreInstanceState(state)
	}
}

interface Contract {
	interface View {
		fun save(bundle: Bundle)
		fun restore(bundle: Bundle)
	}
}

class State {

}

@Scope annotation class ViewScope

@Module class ComponentModule(val state: State) {
	@Provides fun state(): State {
		return state
	}
}

@Subcomponent(modules = arrayOf(ComponentModule::class))
@ViewScope interface ComponentComponent {
	fun inject(view: FusionByComponentsView)
}

class ComponentsAdapter
@Inject constructor(val repo: DataRepo) :
		EpoxyAdapter() {

	init {

	}
}

class PersonaPickerModel : EpoxyModel<PersonaPickerView>() {

	override fun getDefaultLayout(): Int {

	}

	override fun bind(view: PersonaPickerView?) {
		super.bind(view)
	}

	override fun unbind(view: PersonaPickerView?) {

	}
}




