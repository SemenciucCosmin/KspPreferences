package io.github.semenciuccosmin.preferences.sampleandroid.app

import android.app.Application
import io.github.semenciuccosmin.preferences.sampleandroid.initializer.KoinInitializer
import org.koin.android.ext.koin.androidContext

class SampleAndroidApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        KoinInitializer.initKoin(
            context = this@SampleAndroidApplication,
        ) {
            androidContext(this@SampleAndroidApplication)
        }
    }
}
