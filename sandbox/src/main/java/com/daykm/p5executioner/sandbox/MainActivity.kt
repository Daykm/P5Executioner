package com.daykm.p5executioner.sandbox

import android.Manifest
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.daykm.p5executioner.database.init
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okio.buffer
import okio.sink
import okio.source
import timber.log.Timber
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = init(applicationContext)
        findViewById<View>(R.id.dbdump).setOnClickListener {
            dumpDb(this, db)
        }

        GlobalScope.launch {
            val personas = db.dao.personas()
            personas.forEach {
                Timber.i("${it.name}")
            }
        }


        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }
}


fun dumpDb(ctx: Context, db: RoomDatabase) {

    val internalDb = File(db.openHelper.readableDatabase.path)

    val externalFileDb = File(Environment.getExternalStorageDirectory(), "p5.db")

    externalFileDb.delete()

    GlobalScope.launch {
        externalFileDb.sink().buffer().writeAll(internalDb.source())
    }
}