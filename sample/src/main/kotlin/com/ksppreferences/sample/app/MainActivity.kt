package com.ksppreferences.sample.app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.ksppreferences.sample.data.preferences.SamplePreferences
import com.ksppreferences.sample.data.preferences.SamplePreferencesImpl
import com.ksppreferences.sample.ui.component.MainScreen
import com.ksppreferences.sample.ui.theme.KspPreferencesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val samplePreferences: SamplePreferences = SamplePreferencesImpl(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.lifecycleScope.launch {
            Log.d("TESTMESSAGE", "${samplePreferences.getString()}")
            samplePreferences.setString("Hello World")
            Log.d("TESTMESSAGE", "${samplePreferences.getString()}")
        }

        enableEdgeToEdge()
        setContent {
            KspPreferencesTheme {
                MainScreen()
            }
        }
    }
}