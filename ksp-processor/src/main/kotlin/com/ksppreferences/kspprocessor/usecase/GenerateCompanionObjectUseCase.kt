package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.ByteArrayPreference
import com.ksppreferences.annotations.DoublePreference
import com.ksppreferences.annotations.FloatPreference
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.LongPreference
import com.ksppreferences.annotations.Set
import com.ksppreferences.annotations.StringPreference
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations

internal class GenerateCompanionObjectUseCase(
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
    private val getPreferencesNameUseCase: GetPreferencesNameUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): String? {
        val preferencesName = getPreferencesNameUseCase(interfaceDeclaration)
        val preferencesPairs = interfaceDeclaration.getAllFunctions().mapNotNull { function ->
            val (preferencesKeyName, _, preferencesDefaultValueType) = getValueTypeAnnotationData(
                function = function
            )

            val preferencesType = when (preferencesDefaultValueType) {
                Boolean::class.simpleName -> BOOLEAN_PREFERENCES_KEY_NAME
                ByteArray::class.simpleName -> BYTE_ARRAY_PREFERENCES_KEY_NAME
                Double::class.simpleName -> DOUBLE_PREFERENCES_KEY_NAME
                Float::class.simpleName -> FLOAT_PREFERENCES_KEY_NAME
                Int::class.simpleName -> INT_PREFERENCES_KEY_NAME
                Long::class.simpleName -> LONG_PREFERENCES_KEY_NAME
                String::class.simpleName -> STRING_PREFERENCES_KEY_NAME
                else -> return@mapNotNull null
            }

            preferencesKeyName to preferencesType
        }.distinctBy { (preferencesKeyName, _) -> preferencesKeyName }.toList()

        if (preferencesPairs.isEmpty()) return null

        return buildString {
            appendLine(
                """
                |   companion object {
                |       private const val PREFERENCES_NAME = "$preferencesName"
                """.trimMargin()
            )

            preferencesPairs.forEach { (preferencesKeyName, preferencesType) ->
                appendLine(
                    """
                    |       private val $preferencesKeyName = $preferencesType(name = "$preferencesKeyName")
                    """.trimMargin()
                )
            }

            appendLine(
                """
                |   }
                """.trimMargin()
            )
        }
    }

    companion object {
        private const val BOOLEAN_PREFERENCES_KEY_NAME = "booleanPreferencesKey"
        private const val BYTE_ARRAY_PREFERENCES_KEY_NAME = "byteArrayPreferencesKey"
        private const val DOUBLE_PREFERENCES_KEY_NAME = "doublePreferencesKey"
        private const val FLOAT_PREFERENCES_KEY_NAME = "floatPreferencesKey"
        private const val INT_PREFERENCES_KEY_NAME = "intPreferencesKey"
        private const val LONG_PREFERENCES_KEY_NAME = "longPreferencesKey"
        private const val STRING_PREFERENCES_KEY_NAME = "stringPreferencesKey"
    }
}
