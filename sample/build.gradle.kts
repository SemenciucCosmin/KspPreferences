import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_JAVA
import io.gitlab.arturbosch.detekt.extensions.DetektExtension.Companion.DEFAULT_SRC_DIR_KOTLIN

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.detekt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.publish)
}

android {
    namespace = "io.github.semenciuccosmin.preferences.sample"
    compileSdk {
        version = release(libs.versions.compileSdk.get().toInt()) {
            minorApiLevel = 1
        }
    }

    defaultConfig {
        applicationId = "io.github.semenciuccosmin.preferences.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
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
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // COMPOSE
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    // DATA STORE
    implementation(libs.data.store)

    // DETEKT
    detektPlugins(libs.detekt.formatting)

    // KOIN
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // KOTLIN
    implementation(libs.kotlinx.serialization.json)

    // KSP PREFERENCES MODULES
    implementation(projects.annotations)
    ksp(projects.compiler)

    // TEST
    androidTestImplementation(platform(libs.koin.bom))
    androidTestImplementation(projects.annotations)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.junit.ext)
    androidTestImplementation(libs.kotlinx.coroutines.test)
    androidTestImplementation(libs.koin.android.test)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.test.runner)
    androidTestImplementation(libs.turbine)
}

detekt {
    source.setFrom(
        DEFAULT_SRC_DIR_JAVA,
        DEFAULT_SRC_DIR_KOTLIN,
        "${project.rootDir}/annotations/$DEFAULT_SRC_DIR_JAVA",
        "${project.rootDir}/annotations/$DEFAULT_SRC_DIR_KOTLIN",
        "${project.rootDir}/compiler/$DEFAULT_SRC_DIR_JAVA",
        "${project.rootDir}/compiler/$DEFAULT_SRC_DIR_KOTLIN",

    )
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
    config.setFrom("detekt-config.yml")
    baseline = file("detekt-baseline.xml")
}