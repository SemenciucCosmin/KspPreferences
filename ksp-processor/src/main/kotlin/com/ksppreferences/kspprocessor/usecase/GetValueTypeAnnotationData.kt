package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations

internal class GetValueTypeAnnotationData {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Triple<String, Any, String?>? {
        val valueTypeAnnotation = ValueTypeAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val annotation = function.annotations.firstOrNull {
            it.shortName.asString() == valueTypeAnnotation?.simpleName
        } ?: return null

        val preferencesKeyName = annotation.arguments.firstOrNull {
            it.name?.asString() == PREFERENCES_KEY_NAME
        }?.value as? String ?: return null

        val preferencesDefaultValue = annotation.arguments.firstOrNull {
            it.name?.asString() == PREFERENCES_DEFAULT_VALUE_TYPE_NAME
        }?.value ?: return null

        val preferencesDefaultValueTypeName = when (preferencesDefaultValue) {
            is Boolean -> Boolean::class.simpleName
            is ByteArray -> ByteArray::class.simpleName
            is Double -> Double::class.simpleName
            is Float -> Float::class.simpleName
            is Int -> Int::class.simpleName
            is Long -> Long::class.simpleName
            is String -> String::class.simpleName
            else -> Any::class.simpleName
        }

        val defaultValue = when (preferencesDefaultValue) {
            is String -> "\"$preferencesDefaultValue\""
            else -> preferencesDefaultValue
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
