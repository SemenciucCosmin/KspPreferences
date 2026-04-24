package io.github.semenciuccosmin.preferences.sample.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.semenciuccosmin.preferences.sample.ui.viewmodel.model.SampleUiState

/**
 * Root screen composable for the KSP Preferences sample application.
 *
 * Displays a centred welcome message inside a [Scaffold] that respects system window
 * insets via its padding values.
 */
@Composable
fun MainScreen(uiState: SampleUiState) {
    Scaffold { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Text(
                text = "Welcome to KSP Preferences Sample App!",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.size(24.dp))

            uiState.tests.forEach { test ->
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = test.type.name,
                        style = MaterialTheme.typography.titleMedium,
                    )

                    when (test.status) {
                        SampleUiState.Test.Status.RUNNING -> {
                            CircularProgressIndicator(modifier = Modifier.size(16.dp))
                        }

                        SampleUiState.Test.Status.PASSED -> {
                            Text(
                                text = test.status.name,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Green
                            )
                        }

                        SampleUiState.Test.Status.FAILED -> {
                            Text(
                                text = test.status.name,
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Green
                            )
                        }
                    }
                }
            }
        }
    }
}
