package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.logger.Logger

internal class GenerateCompanionObjectUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
    private val getPreferencesNameUseCase: GetPreferencesNameUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): String? {
        val interfaceName = interfaceDeclaration.simpleName.asString()
        val preferencesName = getPreferencesNameUseCase(interfaceDeclaration)
        val valueTypeFunctions = interfaceDeclaration.getAllFunctions().filter { function ->
            ValueTypeAnnotations.all.any { function.isAnnotationPresent(it) }
        }

        val preferencesPairs = valueTypeFunctions.mapNotNull { function ->
            val functionName = function.simpleName.asString()
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
                else -> {
                    logger.logMissingFunctionAnnotationDataError(
                        interfaceName = interfaceName,
                        functionName = functionName
                    )
                    return@mapNotNull null
                }
            }

            preferencesKeyName to preferencesType
        }.distinctBy { (preferencesKeyName, _) -> preferencesKeyName }.toList()

        if (preferencesPairs.isEmpty()) {
            logger.logMissingInterfaceAnnotationDataError(interfaceName)
            return null
        }

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
