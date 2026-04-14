import java.util.Properties

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.publish) apply false
    alias(libs.plugins.sonar)
}

val localProperties = Properties().apply {
    load(rootProject.file("local.properties").inputStream())
}

sonar {
    properties {
        property("sonar.projectKey", "SemenciucCosmin_KspPreferences")
        property("sonar.organization", "semenciuccosmin")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.token", localProperties.getProperty("SONAR_TOKEN") ?: "")
    }
}
