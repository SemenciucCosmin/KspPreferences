package com.ksppreferences.annotations

/**
 * Marks a suspend function as a DataStore **write** operation.
 *
 * Must be combined with a value-type annotation (e.g. [StringPreference], [IntPreference])
 * that declares the preference key. The generated implementation will suspend and persist
 * the supplied value to the DataStore under the declared key.
 *
 * Requirements:
 * - The function must be `suspend`.
 * - The function must declare exactly one parameter whose type matches the value type
 *   declared by the paired preference annotation.
 * - The function must not have a return type (i.e. return `Unit`).
 *
 * Example:
 * ```kotlin
 * @Set
 * @IntPreference(key = "score", defaultValue = 0)
 * suspend fun setScore(value: Int)
 * ```
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Set
