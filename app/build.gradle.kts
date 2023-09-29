plugins {
    kotlin("kapt")
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.hilt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.androidx.navigation.safeargs)
}


android {
    namespace = "com.nelc.cakesizer"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.nelc.cakesizer"
        minSdk = 26
        targetSdk = 33
        versionCode = 6
        versionName = "1.0.5"
        resourceConfigurations += listOf("en", "b+zh+HK")
        setProperty("archivesBaseName", "nelc-cakesizer-$versionName")

    }

    buildTypes {
        getByName("debug") {}
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
            isCrunchPngs = false
            ndk {
                debugSymbolLevel = "FULL"
            }
        }
    }

    splits {
        // Configures multiple APKs based on ABI.
        abi {
            // Enables building multiple APKs per ABI.
            isEnable = true
            // By default all ABIs are included, so use reset() and include to specify that you only
            // want APKs for x86 and x86_64.

            // Resets the list of ABIs for Gradle to create APKs for to none.
            reset()
            // Specifies a list of ABIs for Gradle to create APKs for.
            //noinspection ChromeOsAbiSupport
            include("arm64-v8a", "x86_64")

            // Specifies that you don't want to also generate a universal APK that includes all ABIs.
            isUniversalApk = false
        }
    }

    kotlin {
        jvmToolchain(17)
    }

    kapt {
        // useBuildCache = false
        correctErrorTypes = true
    }

    hilt {
        enableAggregatingTask = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }

    androidResources {
        noCompress += listOf("filamat", "ktx")
    }
    // We use the .filamat extension for materials compiled with matc
    // Telling aapt to not compress them allows to load them efficiently

    packaging {
        resources.excludes += "DebugProbesKt.bin"
    }
}

dependencies {
    implementation(fileTree("dir" to "libs", "include" to listOf("*.jar")))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    implementation(libs.appcompat)

    implementation(libs.core.common)
    implementation(libs.core.runtime)

    implementation(libs.core.ktx)
    implementation(libs.core.splashscreen)

    implementation(libs.savedstate.ktx)

    implementation(libs.lifecycle.common.java8)
    implementation(libs.lifecycle.livedata.core.ktx)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.reactivestreams.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.lifecycle.viewmodel.savedstate)

    implementation(libs.work.runtime.ktx)

    implementation(libs.constraintlayout)

    implementation(libs.activity.ktx)
    implementation(libs.activity.compose)

    implementation(libs.fragment.ktx)

    // Firebase
    val firebaseBom = platform(libs.firebase.bom)
    implementation(firebaseBom)
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)

    // Compose
    val composeBom = platform(libs.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.core)
    implementation(libs.compose.material.icons.extended)
    // Android Studio Preview support
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    // UI Tests
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)

    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Navigation
    implementation(libs.navigation.runtime.ktx)
    implementation(libs.navigation.fragment.ktx)
    implementation(libs.navigation.ui.ktx)
    implementation(libs.navigation.dynamic.features.fragment)
    androidTestImplementation(libs.navigation.testing)
    implementation(libs.navigation.compose)

    implementation(libs.preference.ktx)

    implementation(libs.material)

    implementation (libs.arcore)

    implementation(libs.filament.android)
    implementation(libs.filament.utils.android)
    implementation(libs.gltfio.android)

    implementation(libs.arrow.core)
    implementation(libs.arrow.fx.coroutines)

    // Logging
    implementation(libs.timber)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.svg)

    // Ktor
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.slf4j.timber)

    // TOML
    // implementation("com.akuleshov7:ktoml-core:0.5.0")
    implementation(libs.toml4j)

    // Accompanist
    implementation(libs.accompanist.placeholder)

    // Datetime
    implementation(libs.kotlinx.datetime)

    // Datastore
    implementation(libs.datastore.preferences)
}


tasks.withType(org.jetbrains.kotlin.gradle.tasks.KaptGenerateStubs::class).configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
