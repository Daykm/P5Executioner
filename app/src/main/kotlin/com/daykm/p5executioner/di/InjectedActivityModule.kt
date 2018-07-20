package com.daykm.p5executioner.di

import android.support.v7.app.AppCompatActivity
import dagger.Binds
import dagger.Module

@Module
abstract class InjectedActivityModule<in T : AppCompatActivity> {

    @Binds
    abstract fun bind(activity: T): AppCompatActivity

}
