package com.daykm.p5executioner.database.di

import android.content.Context
import com.daykm.p5executioner.database.P5Database
import com.daykm.p5executioner.database.init
import dagger.Binds
import dagger.Module

@Module
class DataModule {

    @Binds
    fun database(ctx: Context): P5Database = init(ctx)

    @Binds
    fun dao(db: P5Database) = db.dao

}