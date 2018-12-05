private const val archVersion = "1.1.1"

object Kotlin {
    const val stdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.10"
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:1.3.10"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1"
}

object AndroidConfig {
    const val minSdk = 23
    const val targetSdk = 26
    const val compileSdk = 28
    const val buildTools = "28.0.3"
}

object BuildPlugins {
    const val android = "com.android.tools.build:gradle:3.2.1"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.10"
    const val protobuf = "com.google.protobuf:protobuf-gradle-plugin:0.8.6"
}

object AnnoProcs {
    const val dagger = "com.google.dagger:dagger-compiler:2.16"
    const val daggerAndroid = "com.google.dagger:dagger-android-processor:2.16"
    const val butterknife = "com.jakewharton:butterknife-compiler:8.5.1"
    const val databinding = "com.android.databinding:compiler:3.2.0"
    const val room = "android.arch.persistence.room:compiler:1.1.1"

    const val arch = "androidx.lifecycle:lifecycle-compiler:$archVersion"
}

object AnkoLibs {
    const val commons = "org.jetbrains.anko:anko-commons:0.10.1"
    const val sdk21 = "org.jetbrains.anko:anko-sdk21:0.10.1"
    const val compat = "org.jetbrains.anko:anko-appcompat-v7:0.10.1"
    const val card = "org.jetbrains.anko:anko-cardview-v7:0.10.1"
    const val recycle = "org.jetbrains.anko:anko-recyclerview-v7:0.10.1"
    const val design = "org.jetbrains.anko:anko-design:0.10.1"
}

object JetpackLibs {
    const val constraint = "com.android.support.constraint:constraint-layout:1.1.2"


    const val coreCommon = "android.arch.core:common:$archVersion"
    const val coreRuntime = "android.arch.core:runtime:$archVersion"
    const val coreUtils = "com.android.support:support-core-utils:27.1.1"

    const val supportCompat = "com.android.support:support-compat:27.1.1"
    const val supportFragment = "com.android.support:support-fragment:27.1.1"
    const val supportV7 = "com.android.support:appcompat-v7:27.1.1"
    const val supportDesign = "com.android.support:design:27.1.1"

    const val cardView = "com.android.support:cardview-v7:27.1.1"
    const val recyclerView = "com.android.support:recyclerview-v7:27.1.1"
    const val room = "android.arch.persistence.room:runtime:1.1.1"

    const val viewModel = "android.arch.lifecycle:viewmodel:$archVersion"
    const val liveData = "android.arch.lifecycle:livedata:$archVersion"
    const val lifecycleExt = "android.arch.lifecycle:extensions:$archVersion"
}

object Libs {
    const val butterknife = "com.jakewharton:butterknife:8.5.1"
    const val dagger = "com.google.dagger:dagger:2.16"
    const val daggerAndroid = "com.google.dagger:dagger-android:2.16"
    const val daggerAndroidSupport = "com.google.dagger:dagger-android-support:2.16"

    const val epoxy = "com.airbnb.android:epoxy:2.14.0"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:1.5"
    const val leakCanaryNoOp = "com.squareup.leakcanary:leakcanary-android-no-op:1.5"

    const val moshi = "com.squareup.moshi:moshi:1.6.0"
    const val okio = "com.squareup.okio:okio:2.1.0"

    const val proto = "com.google.protobuf:protobuf-lite:3.0.0"

    const val rxJava = "io.reactivex.rxjava2:rxjava:2.0.8"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.0.1"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.1.0"

    const val timber = "com.jakewharton.timber:timber:4.5.1"
}

object TestingLibs {
    const val junit = "junit:junit:4.12"
}

object AndroidTestingLibs {
    const val espressoCore = "com.android.support.test.espresso:espresso-core:3.0.2"
    const val runner = "com.android.support.test:runner:1.0.2"
    const val rules = "com.android.support.test:rules:1.0.2"
}
