package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations

internal class GetValueTypeAnnotationData {

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
