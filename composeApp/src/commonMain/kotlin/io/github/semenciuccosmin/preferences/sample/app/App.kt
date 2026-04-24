package io.github.semenciuccosmin.preferences.sample.app

import androidx.compose.runtime.*
import io.github.semenciuccosmin.preferences.sample.ui.component.MainScreen
import io.github.semenciuccosmin.preferences.sample.ui.theme.KspPreferencesTheme

@Composable
fun App() {
    KspPreferencesTheme {
        MainScreen()
    }
}