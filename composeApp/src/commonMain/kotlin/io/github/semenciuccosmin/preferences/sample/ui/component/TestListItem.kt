package io.github.semenciuccosmin.preferences.sample.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.semenciuccosmin.preferences.sample.ui.viewmodel.model.SampleUiState

@Composable
fun TestListItem(
    title: String,
    status: SampleUiState.Test.Status,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.weight(1f))

            when (status) {
                SampleUiState.Test.Status.RUNNING -> {
                    CircularProgressIndicator(
                        modifier = Modifier.size(16.dp)
                    )
                }

                SampleUiState.Test.Status.PASSED -> {
                    Text(
                        text = status.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Green
                    )
                }

                SampleUiState.Test.Status.FAILED -> {
                    Text(
                        text = status.name,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Red
                    )
                }
            }
        }
    }
}