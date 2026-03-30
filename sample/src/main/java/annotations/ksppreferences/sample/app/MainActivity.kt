package annotations.ksppreferences.sample.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import annotations.ksppreferences.sample.ui.component.MainScreen
import annotations.ksppreferences.sample.ui.theme.KspPreferencesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _root_ide_package_.annotations.ksppreferences.sample.ui.theme.KspPreferencesTheme {
                _root_ide_package_.annotations.ksppreferences.sample.ui.component.MainScreen()
            }
        }
    }
}