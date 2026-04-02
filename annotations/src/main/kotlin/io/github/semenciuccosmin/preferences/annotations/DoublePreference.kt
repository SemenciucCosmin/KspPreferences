package io.github.semenciuccosmin.preferences.annotations

/**
 * Declares a **Double** DataStore preference for the annotated function.
 *
 * Pair with [Get], [GetFlow], or [Set] to generate the corresponding DataStore operation.
 *
 * @param key          The DataStore preferences key under which the value is stored.
 * @param defaultValue The value returned when the key is not present in the DataStore.
 *                     Defaults to `0.0`.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class DoublePreference(
    val key: String,
    val defaultValue: Double = 0.0
)
