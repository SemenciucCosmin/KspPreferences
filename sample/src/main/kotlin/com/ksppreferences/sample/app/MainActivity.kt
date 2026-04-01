package com.ksppreferences.sample.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ksppreferences.sample.ui.component.MainScreen
import com.ksppreferences.sample.ui.theme.KspPreferencesTheme

class MainActivity : ComponentActivity() {

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