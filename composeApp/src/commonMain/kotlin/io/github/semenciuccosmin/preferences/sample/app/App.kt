package io.github.semenciuccosmin.preferences.sample.app

import androidx.compose.runtime.*
import io.github.semenciuccosmin.preferences.sample.ui.component.MainScreen
import io.github.semenciuccosmin.preferences.sample.ui.theme.KspPreferencesTheme
import io.github.semenciuccosmin.preferences.sample.ui.viewmodel.SampleViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    KspPreferencesTheme {
        val viewModel: SampleViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        MainScreen(uiState = uiState)
    }
}