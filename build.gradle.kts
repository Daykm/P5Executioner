// Top-level build file where you can add configuration options common to all sub-projects/modules.
apply(mapOf(Pair("from", "config.gradle")))

buildscript {
    ext.buildVer = [

    kotlin        : '1.1.2-4',
    androidGradle : '3.0.0-alpha1',
    protobufGradle: '0.8.1'

    ]

    repositories {
        maven {
            url "https://maven.google.com"
        }
        jcenter()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:$buildVer.androidGradle"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$buildVer.kotlin"
        classpath "com.google.protobuf:protobuf-gradle-plugin:$buildVer.protobufGradle"
        classpath(kotlinModule("gradle-plugin"))
    }
}

allprojects {
    repositories {
        maven {
            url "https://maven.google.com"
        }
        jcenter()
    }
}

task clean (type: Delete) {
    delete rootProject . buildDir
}
