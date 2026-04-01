package com.garmin.connectiq.di

import com.ksppreferences.sample.data.preferences.SamplePreferences
import com.ksppreferences.sample.data.preferences.SamplePreferencesImpl
import org.koin.dsl.module

fun samplePreferencesTest() = module {
    single<SamplePreferences> { SamplePreferencesImpl(get()) }
}
