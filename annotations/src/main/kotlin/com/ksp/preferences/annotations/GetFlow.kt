package com.ksp.preferences.annotations

/**
 * Marks a function as a DataStore **reactive read** operation that returns a [kotlinx.coroutines.flow.Flow].
 *
 * Must be combined with a value-type annotation (e.g. [StringPreference], [IntPreference])
 * that declares the preference key and default value. The generated implementation maps the
 * DataStore data stream and emits the stored value on every change, or the declared default
 * when the key is absent.
 *
 * Requirements:
 * - The function must **not** be `suspend`.
 * - The function must have no parameters.
 * - The return type must be `Flow<T>` where `T` matches the value type declared by the
 *   paired preference annotation.
 *
 * Example:
 * ```kotlin
 * @GetFlow
 * @IntPreference(key = "score", defaultValue = 0)
 * fun getScoreFlow(): Flow<Int>
 * ```
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class GetFlow
