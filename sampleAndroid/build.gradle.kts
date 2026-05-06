plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.serialization)
    kotlin("android")
}

android {
    namespace = "io.github.semenciuccosmin.preferences.sampleandroid"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.github.semenciuccosmin.preferences.sampleandroid"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // ANDROIDX
    implementation(libs.androidx.appcompat)

    // COMPOSE
    implementation(platform(libs.compose.bom))
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    // DATA STORE
    implementation(libs.data.store)
    implementation(libs.data.store.preferences)

    // KOIN
    implementation(libs.koin.core)
    implementation(libs.koin.compose)
    implementation(libs.koin.compose.viewmodel)

    // KOTLINX
    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.immutableCollections)
    implementation(libs.kotlinx.serialization.json)

    // MODULES
    implementation(projects.annotations)
    ksp(projects.compiler)
}
