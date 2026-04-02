plugins {
    alias(libs.plugins.publish)
    kotlin("jvm")
}

mavenPublishing {
    coordinates(
        groupId = "io.github.semenciuccosmin",
        artifactId = "preferences-annotations",
        version = "1.0.0"
    )
}
