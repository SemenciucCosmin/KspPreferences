import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    jvm()


    sourceSets {
        androidMain.dependencies {
            // ANDROIDX
            implementation(libs.androidx.appcompat)
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.material)

            // KOIN
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            // COMPOSE
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.runtime)
            implementation(compose.ui)

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
        }
        commonTest.dependencies {
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
        }
        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.androidx.test.core)
                implementation(libs.junit)
                implementation(libs.junit.ext)
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.koin.android)
                implementation(libs.koin.android.test)
                implementation(libs.koin.test)
                implementation(libs.mockk.android)
                implementation(libs.test.runner)
                implementation(libs.turbine)
                implementation(projects.annotations)
            }
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "io.github.semenciuccosmin.preferences.sample"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "io.github.semenciuccosmin.preferences.sample"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    detektPlugins(libs.detekt.formatting)
    debugImplementation(libs.compose.ui.tooling)

    // KSP processor for all targets
    add("kspAndroid", projects.compiler)
    add("kspJvm", projects.compiler)
    add("kspIosArm64", projects.compiler)
    add("kspIosSimulatorArm64", projects.compiler)
}

compose.desktop {
    application {
        mainClass = "io.github.semenciuccosmin.preferences.sample.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.github.semenciuccosmin.preferences.sample"
            packageVersion = "1.0.0"
        }
    }
}

detekt {
    source.setFrom(
        // APP
        "${project.rootDir}/composeApp/src/androidMain/kotlin",
        "${project.rootDir}/composeApp/src/commonMain/kotlin",
        "${project.rootDir}/composeApp/src/iosMain/kotlin",
        "${project.rootDir}/composeApp/src/jvmMain/kotlin",

        // ANNOTATIONS
        "${project.rootDir}/annotations/src/main/kotlin",

        // COMPILER
        "${project.rootDir}/compiler/src/main/kotlin",
    )

    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = true
    config.setFrom("detekt/detekt-config.yml")
    baseline = file("detekt/detekt-baseline.xml")
}
