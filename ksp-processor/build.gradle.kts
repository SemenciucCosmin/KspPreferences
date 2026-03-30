plugins {
    kotlin("jvm")
}

dependencies {
    // KSP
    implementation(libs.symbol.processing)

    // KSP PREFERENCES MODULES
    implementation(projects.annotations)
}