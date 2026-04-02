package com.ksppreferences.annotations

/**
 * Marks an interface as a KSP-managed DataStore preferences container.
 *
 * The KSP processor will generate a concrete implementation of the annotated interface
 * backed by [androidx.datastore.preferences.core.Preferences].
 *
 * @param name The name used for the underlying DataStore file. Must be unique per
 *             DataStore within the application.
 *
 * Example usage:
 * ```kotlin
 * @Preferences(name = "user_preferences")
 * interface UserPreferences {
 *     @Get
 *     @StringPreference(key = "username", defaultValue = "")
 *     suspend fun getUsername(): String
 * }
 * ```
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.SOURCE)
annotation class Preferences(val name: String)
