package io.github.semenciuccosmin.preferences.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import io.github.semenciuccosmin.preferences.sample.app.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "KspPreferences",
    ) {
        App()
    }
}