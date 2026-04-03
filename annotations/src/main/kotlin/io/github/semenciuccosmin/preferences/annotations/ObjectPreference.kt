package io.github.semenciuccosmin.preferences.annotations

import kotlin.reflect.KClass

/**
 * Declares a **serializable object** DataStore preference for the annotated function.
 *
 * The object is serialized to a JSON string via `kotlinx.serialization` and stored under
 * a `stringPreferencesKey`. On read, the JSON string is deserialized back to an instance
 * of [clazz].
 *
 * Pair with [Get], [GetFlow], or [Set] to generate the corresponding DataStore operation.
 * The generated getter returns a nullable type (`T?`), yielding `null` when the key is
 * absent instead of a primitive default value.
 *
 * @param key   The DataStore preferences key under which the serialized value is stored.
 * @param clazz The [KClass] of the serializable object type to store and retrieve.
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ObjectPreference(
    val key: String,
    val clazz: KClass<*>
)
