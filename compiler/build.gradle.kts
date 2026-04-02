plugins {
    alias(libs.plugins.publish)
    kotlin("jvm")
}

mavenPublishing {
    coordinates(
        groupId = "io.github.semenciuccosmin",
        artifactId = "preference-compiler",
        version = "1.0.0"
    )
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