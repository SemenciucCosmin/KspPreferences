package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations

/**
 * Extracts the key name, formatted default value, and value type from the value-type
 * annotation present on a function declaration.
 *
 * Returns a [Triple] of `(keyName, defaultValue, typeName)`. Any element may be `null`
 * when the corresponding annotation argument cannot be resolved.
 *
 * Default-value formatting rules applied before returning:
 * - `ByteArray` (represented as `List<*>` at annotation level) → `byteArrayOf(…)` literal.
 * - `Float` → appends the `f` suffix.
 * - `String` → wraps in double quotes.
 * - All other types → plain `toString()`.
 */
internal class GetValueTypeAnnotationData {

    /**
     * @param function The KSP declaration of the annotated function.
     * @return A [Triple] of:
     *   - `first`  — the preference key name string, or `null` if unresolvable.
     *   - `second` — the formatted default value literal string, or `null` if unresolvable.
     *   - `third`  — the simple Kotlin type name (e.g. `"Int"`, `"ByteArray"`), or `null`.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Triple<String?, String?, String?> {
        val valueTypeAnnotation = ValueTypeAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val annotation = function.annotations.firstOrNull {
            it.shortName.asString() == valueTypeAnnotation?.simpleName
        }

        val preferencesKeyName = annotation?.arguments?.firstOrNull {
            it.name?.asString() == PREFERENCES_KEY_NAME
        }?.value as? String

        val preferencesDefaultValue = annotation?.arguments?.firstOrNull {
            it.name?.asString() == PREFERENCES_DEFAULT_VALUE_TYPE_NAME
        }?.value

        val preferencesDefaultValueTypeName = when (preferencesDefaultValue) {
            is Boolean -> Boolean::class.simpleName
            is List<*> -> ByteArray::class.simpleName
            is Double -> Double::class.simpleName
            is Float -> Float::class.simpleName
            is Int -> Int::class.simpleName
            is Long -> Long::class.simpleName
            is String -> String::class.simpleName
            else -> Any::class.simpleName
        }


        val defaultValue = when (preferencesDefaultValue) {
            is List<*> -> {
                preferencesDefaultValue.toString()
                    .replace("[", "byteArrayOf(")
                    .replace("]", ")")
            }

            is Float -> "${preferencesDefaultValue}f"

            is String -> "\"$preferencesDefaultValue\""

            else -> preferencesDefaultValue.toString()
        }

        return Triple(
            preferencesKeyName,
            defaultValue,
            preferencesDefaultValueTypeName
        )
    }

    companion object {
        private const val PREFERENCES_KEY_NAME = "key"
        private const val PREFERENCES_DEFAULT_VALUE_TYPE_NAME = "defaultValue"
    }
}
