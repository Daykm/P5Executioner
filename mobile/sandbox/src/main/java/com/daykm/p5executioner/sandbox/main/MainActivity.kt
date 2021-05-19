package com.daykm.p5executioner.sandbox.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.room.RoomDatabase
import com.daykm.p5executioner.sandbox.R
import com.daykm.p5executioner.sandbox.fusion.FusionSandboxActivity
import com.daykm.p5executioner.sandbox.personas.PersonasSandboxActivity
import com.daykm.p5executioner.sandbox.skills.SkillsSandboxActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okio.buffer
import okio.sink
import okio.source
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list_butt.setOnClickListener {
            startActivity(Intent(this@MainActivity, PersonasSandboxActivity::class.java))
        }

        skills_butt.setOnClickListener {
            startActivity(Intent(this@MainActivity, SkillsSandboxActivity::class.java))
        }

        fusion_butt.setOnClickListener {
            startActivity(Intent(this@MainActivity, FusionSandboxActivity::class.java))
        }

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }
}


fun dumpDb(db: RoomDatabase) {

    val internalDb = File(db.openHelper.readableDatabase.path)

    val externalFileDb = File(Environment.getExternalStorageDirectory(), "p5.db")

    externalFileDb.delete()

    GlobalScope.launch {
        externalFileDb.sink().buffer().writeAll(internalDb.source())
    }
}