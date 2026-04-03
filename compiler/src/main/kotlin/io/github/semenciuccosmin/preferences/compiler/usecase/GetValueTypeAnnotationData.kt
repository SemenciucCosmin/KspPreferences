package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.semenciuccosmin.preferences.compiler.annotations.ValueTypeAnnotations
import io.github.semenciuccosmin.preferences.compiler.model.AnnotationData

/**
 * Extracts the key name, formatted default value, and value type from the value-type
 * annotation present on a function declaration.
 *
 * Returns a [Triple] of `(keyName, defaultValue, typeName)`. Any element may be `null`
 * when the corresponding annotation argument cannot be resolved.
 *
 * Default-value formatting rules applied before returning:
 * - `Float` → appends the `f` suffix.
 * - `String` → wraps in double quotes.
 * - All other types → plain `toString()`.
 */
internal class GetValueTypeAnnotationData {

    /**
     * Extracts annotation metadata from [function].
     *
     * @param function The KSP declaration of the annotated function.
     * @return An [AnnotationData] containing the key name, formatted default value, and type
     *         information. Any field may be `null` when the corresponding annotation argument
     *         cannot be resolved.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): AnnotationData {
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

        val preferencesClazz = annotation?.arguments?.firstOrNull {
            it.name?.asString() == PREFERENCES_CLAZZ_NAME
        }?.value

        val defaultValue = when (preferencesDefaultValue) {
            is Float -> "${preferencesDefaultValue}f"
            is String -> "\"$preferencesDefaultValue\""
            else -> preferencesDefaultValue.toString()
        }

        return AnnotationData(
            keyName = preferencesKeyName,
            defaultValue = defaultValue,
            primitiveType = preferencesDefaultValue,
            objectType = preferencesClazz
        )
    }

    companion object {
        private const val PREFERENCES_KEY_NAME = "key"
        private const val PREFERENCES_DEFAULT_VALUE_TYPE_NAME = "defaultValue"
        private const val PREFERENCES_CLAZZ_NAME = "clazz"
    }
}
