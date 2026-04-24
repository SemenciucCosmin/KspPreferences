package io.github.semenciuccosmin.preferences.annotations

/**
 * Marks a suspend function as a DataStore **read** operation.
 *
 * Must be combined with a value-type annotation (e.g. [StringPreference], [IntPreference])
 * that declares the preference key and default value. The generated implementation will
 * suspend, read the current snapshot from the DataStore, and return the stored value or
 * the declared default if the key is absent.
 *
 * Requirements:
 * - The function must be `suspend`.
 * - The function must have no parameters.
 * - The return type must match the value type declared by the paired preference annotation.
 *
 * Example:
 * ```kotlin
 * @Get
 * @IntPreference(key = "score", defaultValue = 0)
 * suspend fun getScore(): Int
 * ```
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Get
