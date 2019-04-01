private const val archVersion = "1.1.1"

object Kotlin {
    const val stdLibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.21"
    const val stdLib = "org.jetbrains.kotlin:kotlin-stdlib:1.3.21"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1"
}

object AndroidConfig {
    const val minSdk = 22
    const val targetSdk = 26
    const val compileSdk = 28
    const val buildTools = "28.0.3"
}

object BuildPlugins {
    const val android = "com.android.tools.build:gradle:3.3.2"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.21"
    const val protobuf = "com.google.protobuf:protobuf-gradle-plugin:0.8.6"
}

object AnnoProcs {
    const val databinding = "com.android.databinding:compiler:3.3.1"
}

object JetpackLibs {
    const val constraint = "androidx.constraintlayout:constraintlayout:1.1.2"

    private const val archVer = "2.0.1"
    const val coreCommon = "androidx.arch.core:core-common:$archVer"
    const val coreRuntime = "androidx.arch.core:core-runtime:$archVer"
    //const val coreUtils = "com.android.support:support-core-utils:$supportLibVersion"

    private const val appCompat = "1.1.0-alpha03"
    const val compat = "androidx.appcompat:appcompat:$appCompat"
    const val fragment = "androidx.fragment:fragment:$appCompat"

    const val design = "com.google.android.material:material:1.0.0-rc01"

    const val cardView = "androidx.cardview:cardview:1.0.0"
    const val recyclerView = "androidx.recyclerview:recyclerview:1.0.0"
    const val viewpager = "androidx.viewpager:viewpager:1.0.0"
}

object Lifecycle {

    private const val lifecycle_version = "2.0.0"

    // ViewModel and LiveData
    const val extensions = "androidx.lifecycle:lifecycle-extensions:$lifecycle_version"
    // alternatively - just ViewModel
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version" // For Kotlin use lifecycle-viewmodel-ktx
    // alternatively - just LiveData
    const val livedata = "androidx.lifecycle:lifecycle-livedata:$lifecycle_version"
    // alternatively - Lifecycles only (no ViewModel or LiveData). Some UI
    //     AndroidX libraries use this lightweight import for Lifecycle
    const val runtime = "androidx.lifecycle:lifecycle-runtime:$lifecycle_version"

    const val compiler = "androidx.lifecycle:lifecycle-compiler:$lifecycle_version" // For Kotlin use kapt instead of annotationProcessor
    // alternately - if using Java8, use the following instead of lifecycle-compiler
    const val java8 = "androidx.lifecycle:lifecycle-common-java8:$lifecycle_version"

    // optional - ReactiveStreams support for LiveData
    const val rx = "androidx.lifecycle:lifecycle-reactivestreams:$lifecycle_version" // For Kotlin use lifecycle-reactivestreams-ktx
    // optional - ReactiveStreams support for LiveData
    const val rxKtx = "androidx.lifecycle:lifecycle-reactivestreams-ktx:$lifecycle_version" // For Kotlin use lifecycle-reactivestreams-ktx

    // optional - Test helpers for LiveData
    const val test = "androidx.arch.core:core-testing:$lifecycle_version"

}

object Room {
    private const val room_version = "2.1.0-alpha06"

    const val runtime = "androidx.room:room-runtime:$room_version"
    const val compiler = "androidx.room:room-compiler:$room_version" // For Kotlin use kapt instead of annotationProcessor

    // optional - Kotlin Extensions and Coroutines support for Room
    const val ktx = "androidx.room:room-ktx:$room_version"

    // optional - RxJava support for Room
    const val rxjava2 = "androidx.room:room-rxjava2:$room_version"

    // optional - Guava support for Room, including Optional and ListenableFuture
    const val guava = "androidx.room:room-guava:$room_version"

    // Test helpers
    const val test = "androidx.room:room-testing:$room_version"
}

object Dagger {
    private const val version = "2.21"
    const val compiler = "com.google.dagger:dagger-compiler:$version"
    const val androidCompiler = "com.google.dagger:dagger-android-processor:$version"
    const val runtime = "com.google.dagger:dagger:$version"
    const val android = "com.google.dagger:dagger-android:$version"
    const val androidSupport = "com.google.dagger:dagger-android-support:$version"
}

object Libs {

    const val epoxy = "com.airbnb.android:epoxy:3.3.0"

    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:1.6.3"
    const val leakCanaryNoOp = "com.squareup.leakcanary:leakcanary-android-no-op:1.6.3"

    const val moshi = "com.squareup.moshi:moshi:1.8.0"
    const val okio = "com.squareup.okio:okio:2.2.0"

    const val proto = "com.google.protobuf:protobuf-lite:3.0.0"

    const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.8"
    const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"

    const val timber = "com.jakewharton.timber:timber:4.7.1"
}

object TestingLibs {
    const val junit = "junit:junit:4.12"
}

object AndroidTestingLibs {
    const val espressoCore = "com.android.support.test.espresso:espresso-core:3.0.2"
    const val runner = "com.android.support.test:runner:1.0.2"
    const val rules = "com.android.support.test:rules:1.0.2"
}
