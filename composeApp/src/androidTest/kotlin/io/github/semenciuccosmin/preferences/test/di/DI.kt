package io.github.semenciuccosmin.preferences.test.di

import io.github.semenciuccosmin.preferences.factory.PreferencesFactory
import io.github.semenciuccosmin.preferences.factory.create
import io.github.semenciuccosmin.preferences.sample.data.preferences.SamplePreferences
import io.github.semenciuccosmin.preferences.sample.data.preferences.SamplePreferencesConstructor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


/**
 * Koin module used exclusively by instrumented tests.
 *
 * Registers [SamplePreferences] as a **singleton** so that every test in the suite shares
 * the same [SamplePreferencesImpl] instance and therefore the same underlying DataStore.
 * This prevents the "multiple DataStores active for the same file" error that arises when
 * more than one [SamplePreferencesImpl] is alive at once.
 */
fun samplePreferencesTest() = module {
    single<SamplePreferences> { PreferencesFactory.create(SamplePreferencesConstructor, androidContext()) }
}
