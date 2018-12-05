package com.daykm.p5executioner.arch

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet
import javax.inject.Inject
import javax.inject.Provider

class MultibindsViewModelFactory
@Inject constructor(
        private val binders: Set<ViewModelBinder<Any>>
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return binders.first { it.clazz == modelClass }.provider.get() as T
    }
}

@Module
interface FactoryModule {
    @Binds
    fun bindViewModelFactory(factory: MultibindsViewModelFactory): ViewModelProvider.Factory
}

class ViewModelBinder(
        val clazz: Class<Any>,
        val provider: Provider
)

@Module(includes = [FactoryModule::class])
abstract class ViewModelModule<T : ViewModel> {

    @Provides
    @IntoSet
    fun bindViewModel(viewModel: T, provider: Provider<T>): ViewModelBinder {
        return ViewModelBinder(viewModel.javaClass, provider)
    }
}