plugins {
    kotlin("jvm")
}

dependencies {
    // KSP
    implementation(libs.symbol.processing)

    // KOTLIN
    implementation(libs.kotlinx.coroutines)

    // KSP PREFERENCES MODULES
    implementation(projects.annotations)
}