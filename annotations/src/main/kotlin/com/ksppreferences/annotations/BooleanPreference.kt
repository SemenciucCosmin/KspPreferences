package com.ksppreferences.annotations

/**
 * Declares a **Boolean** DataStore preference for the annotated function.
 *
 * Pair with [Get], [GetFlow], or [Set] to generate the corresponding DataStore operation.
 *
 * @param key          The DataStore preferences key under which the value is stored.
 * @param defaultValue The value returned when the key is not present in the DataStore.
 *                     Defaults to `false`.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class BooleanPreference(
    val key: String,
    val defaultValue: Boolean = false
)
