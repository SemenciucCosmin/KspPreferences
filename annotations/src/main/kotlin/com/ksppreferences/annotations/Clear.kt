package com.ksppreferences.annotations

/**
 * Marks a suspend function as a DataStore **clear** operation.
 *
 * The generated implementation will suspend and remove all key-value pairs stored in the
 * DataStore, effectively resetting it to an empty state.
 *
 * Requirements:
 * - The function must be `suspend`.
 * - The function must have no parameters.
 * - The function must not have a return type (i.e. return `Unit`).
 *
 * Example:
 * ```kotlin
 * @Clear
 * suspend fun clear()
 * ```
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class Clear
