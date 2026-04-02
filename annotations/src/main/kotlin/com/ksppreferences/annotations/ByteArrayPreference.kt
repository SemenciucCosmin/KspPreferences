package com.ksppreferences.annotations

/**
 * Declares a **ByteArray** DataStore preference for the annotated function.
 *
 * Pair with [Get], [GetFlow], or [Set] to generate the corresponding DataStore operation.
 *
 * @param key          The DataStore preferences key under which the value is stored.
 * @param defaultValue The value returned when the key is not present in the DataStore.
 *                     Defaults to an empty byte array.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ByteArrayPreference(
    val key: String,
    val defaultValue: ByteArray = []
)
