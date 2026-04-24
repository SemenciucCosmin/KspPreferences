package io.github.semenciuccosmin.preferences.sample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform