package io.github.semenciuccosmin.preferences.sample.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Root Material 3 theme composable for the KSP Preferences sample application.
 *
 * Applies dynamic colour on Android 12+ (API 31 and above) when [dynamicColor] is `true`.
 * Falls back to a static dark or light colour scheme based on [darkTheme] on older API levels.
 *
 * @param darkTheme    Whether to use the dark colour scheme. Defaults to the system setting.
 * @param dynamicColor Whether to use Material You dynamic colour (Android 12+ only).
 *                     Defaults to `true`.
 * @param content      The Compose content to render inside the theme.
 */
@Composable
fun KspPreferencesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}