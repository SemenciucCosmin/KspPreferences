package io.github.semenciuccosmin.preferences.sampleandroid.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import io.github.semenciuccosmin.preferences.sampleandroid.ui.component.MainScreen
import io.github.semenciuccosmin.preferences.sampleandroid.ui.theme.SampleAndroidTheme
import io.github.semenciuccosmin.preferences.sampleandroid.ui.viewmodel.SampleViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    SampleAndroidTheme {
        val viewModel: SampleViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()
        MainScreen(uiState = uiState)
    }
}