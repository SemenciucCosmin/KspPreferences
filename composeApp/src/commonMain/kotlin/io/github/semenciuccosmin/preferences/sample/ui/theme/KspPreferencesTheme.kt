package io.github.semenciuccosmin.preferences.sample.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

/**
 * Root Material 3 theme composable for the KSP Preferences sample application.
 *
 * @param darkTheme    Whether to use the dark colour scheme. Defaults to the system setting.
 * @param content      The Compose content to render inside the theme.
 */
@Composable
fun KspPreferencesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}