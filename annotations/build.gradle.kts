plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.publish)
    id("com.android.library")
}

kotlin {
    androidTarget()
    jvm()
    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.data.store.preferences)
            implementation(libs.kotlinx.coroutines)
        }
    }
}

android {
    namespace = "io.github.semenciuccosmin.preferences.annotations"
    compileSdk = 36
    defaultConfig { minSdk = 26 }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = "io.github.semenciuccosmin",
        artifactId = "preferences-annotations",
        version = "2.0.0"
    )

    pom {
        name = "Preferences Annotations"
        description =
            "This module contains the annotations used by the KSP Preferences library. It is a separate module to avoid adding unnecessary dependencies to the main library."
        inceptionYear = "2026"
        url = "https://github.com/SemenciucCosmin/KspPreferences"

        licenses {
            license {
                name = "The Apache License, Version 2.0"
                url = "https://www.apache.org/licenses/LICENSE-2.0.txt"
                distribution = "https://www.apache.org/licenses/LICENSE-2.0.txt"
            }
        }

        developers {
            developer {
                id = "semenciuccosmin"
                name = "Semenciuc Cosmin"
                url = "https://github.com/SemenciucCosmin"
            }
        }

        scm {
            url = "https://github.com/SemenciucCosmin/KspPreferences"
        }
    }
}
