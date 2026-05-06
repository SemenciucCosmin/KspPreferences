package io.github.semenciuccosmin.preferences.sample.app

import android.app.Application
import io.github.semenciuccosmin.preferences.sample.initializer.KoinInitializer
import org.koin.android.ext.koin.androidContext

class KspPreferencesSampleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer.initKoin(
            context = this@KspPreferencesSampleApplication,
        ) {
            androidContext(this@KspPreferencesSampleApplication)
        }
    }
}
