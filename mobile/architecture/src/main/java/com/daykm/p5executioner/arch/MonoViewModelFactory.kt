package com.daykm.p5executioner.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class MonoViewModelFactory<T : ViewModel>
@Inject constructor(
        private val monoProvider: Provider<T>
) : ViewModelProvider.Factory {
    override fun <V : ViewModel> create(modelClass: Class<V>): V {
        val mono = monoProvider.get()
        if (mono.javaClass.isAssignableFrom(modelClass)) {
            @Suppress("UNCHECKED_CAST")
            return mono as V
        } else {
            throw Exception("Expected to inject ${mono.javaClass}, requested to inject $modelClass")
        }
    }
}
