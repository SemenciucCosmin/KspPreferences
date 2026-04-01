plugins {
    kotlin("jvm")
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