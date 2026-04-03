package io.github.semenciuccosmin.preferences.sample.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import io.github.semenciuccosmin.preferences.sample.ui.component.MainScreen
import io.github.semenciuccosmin.preferences.sample.ui.theme.KspPreferencesTheme

/**
 * Entry-point activity for the KSP Preferences sample application.
 *
 * Enables edge-to-edge rendering and hosts [MainScreen] wrapped inside
 * [KspPreferencesTheme].
 */
class MainActivity : ComponentActivity() {

    /** Sets up the Compose UI and enables edge-to-edge window rendering. */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            KspPreferencesTheme {
                MainScreen()
            }
        }
    }
}