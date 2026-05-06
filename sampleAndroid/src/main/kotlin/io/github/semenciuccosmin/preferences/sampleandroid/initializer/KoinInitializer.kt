package io.github.semenciuccosmin.preferences.sampleandroid.initializer

import io.github.semenciuccosmin.preferences.factory.PreferencesFactory
import io.github.semenciuccosmin.preferences.factory.create
import io.github.semenciuccosmin.preferences.sampleandroid.data.preferences.SamplePreferences
import io.github.semenciuccosmin.preferences.sampleandroid.ui.viewmodel.SampleViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

/**
 * Initializes Koin for dependency injection.
 * This function sets up the Koin context with the provided configuration and modules.
 */
object KoinInitializer {

    fun initKoin(context: Any? = null, config: KoinAppDeclaration? = null) {
        startKoin {
            config?.invoke(this)
            modules(
                module {
                    viewModelOf(::SampleViewModel)
                    single<SamplePreferences> { PreferencesFactory.create(context) }
                }
            )
        }
    }
}
