package io.github.semenciuccosmin.preferences.sample

import androidx.compose.ui.window.ComposeUIViewController
import io.github.semenciuccosmin.preferences.sample.app.App
import io.github.semenciuccosmin.preferences.sample.initializer.KoinInitializer

@Suppress("FunctionNaming")
fun MainViewController() = ComposeUIViewController(
    configure = {
        KoinInitializer.initKoin()
    }
) {
    App()
}
