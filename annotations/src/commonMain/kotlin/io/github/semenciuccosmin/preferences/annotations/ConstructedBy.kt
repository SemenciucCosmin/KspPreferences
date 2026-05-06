package io.github.semenciuccosmin.preferences.annotations

import io.github.semenciuccosmin.preferences.factory.PreferencesConstructor
import kotlin.reflect.KClass

/**
 * Links a [@Preferences][Preferences]-annotated interface to its
 * [PreferencesConstructor] object.
 *
 * The user declares an `expect object` implementing [PreferencesConstructor] and
 * references it here. The KSP processor then generates the corresponding `actual object`
 * on every target platform.
 *
 * Example usage:
 * ```kotlin
 * @Preferences(name = "user_preferences")
 * @ConstructedBy(UserPreferencesConstructor::class)
 * interface UserPreferences { … }
 *
 * expect object UserPreferencesConstructor : PreferencesConstructor<UserPreferences>
 * ```
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class ConstructedBy(val value: KClass<out PreferencesConstructor<*>>)
