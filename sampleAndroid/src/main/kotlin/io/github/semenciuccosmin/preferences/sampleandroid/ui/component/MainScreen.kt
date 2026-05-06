package io.github.semenciuccosmin.preferences.sampleandroid.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.semenciuccosmin.preferences.sampleandroid.ui.viewmodel.model.SampleUiState

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

            Spacer(modifier = Modifier.weight(1f))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "Running ${uiState.tests.count()} tests",
                    style = MaterialTheme.typography.titleLarge,
                )

                HorizontalDivider()

                uiState.tests.forEach { test ->
                    TestListItem(
                        title = "Test for ${test.type.name}",
                        status = test.status,
                    )
                }
            }
        }
    }
}
