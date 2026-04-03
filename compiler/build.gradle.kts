plugins {
    alias(libs.plugins.publish)
    kotlin("jvm")
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = "io.github.semenciuccosmin",
        artifactId = "preferences-compiler",
        version = "1.1.0"
    )

    pom {
        name = "Preferences Compiler"
        description = "This module contains the KSP processor for the KSP Preferences library. It is a separate module to avoid adding unnecessary dependencies to the main library."
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



dependencies {
    // KSP
    implementation(libs.symbol.processing)

    // KOIN
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)

    // KOTLIN
    implementation(libs.kotlinx.coroutines)

    // KSP PREFERENCES MODULES
    implementation(projects.annotations)
}