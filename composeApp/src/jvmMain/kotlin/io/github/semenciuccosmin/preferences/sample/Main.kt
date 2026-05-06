package io.github.semenciuccosmin.preferences.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.semenciuccosmin.preferences.sample.app.App
import io.github.semenciuccosmin.preferences.sample.initializer.KoinInitializer

fun main() = application {
    KoinInitializer.initKoin()

    Window(
        onCloseRequest = ::exitApplication,
        title = "KspPreferences",
    ) {
        App()
    }
}
