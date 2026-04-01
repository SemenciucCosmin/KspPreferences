package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Clear
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.Set
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.annotations.FunctionalAnnotations
import com.ksppreferences.kspprocessor.logger.Logger

internal class GenerateFunctionUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
    private val generateGetFunctionUseCase: GenerateGetFunctionUseCase,
    private val generateGetFlowFunctionUseCase: GenerateGetFlowFunctionUseCase,
    private val generateSetFunctionUseCase: GenerateSetFunctionUseCase,
    private val generateClearFunctionUseCase: GenerateClearFunctionUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(
        interfaceName: String,
        function: KSFunctionDeclaration
    ): String? {
        val functionName = function.simpleName.asString()
        val accessorAnnotation = AccessorAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val functionalAnnotation = FunctionalAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val (
            preferencesKeyName,
            preferencesDefaultValue,
            preferencesDefaultValueType,
        ) = getValueTypeAnnotationData(function)

        return when {
            accessorAnnotation == Get::class -> {
                if (preferencesKeyName == null || preferencesDefaultValueType == null) {
                    logger.logMissingFunctionAnnotationDataError(
                        interfaceName = interfaceName,
                        functionName = functionName
                    )
                    return null
                }

                generateGetFunctionUseCase(
                    functionName = functionName,
                    preferencesKeyName = preferencesKeyName,
                    preferencesDefaultValue = preferencesDefaultValue.toString(),
                    preferencesDefaultValueType = preferencesDefaultValueType,
                )
            }

            accessorAnnotation == GetFlow::class -> {
                if (preferencesKeyName == null || preferencesDefaultValueType == null) {
                    logger.logMissingFunctionAnnotationDataError(
                        interfaceName = interfaceName,
                        functionName = functionName
                    )
                    return null
                }

                generateGetFlowFunctionUseCase(
                    functionName = functionName,
                    preferencesKeyName = preferencesKeyName,
                    preferencesDefaultValue = preferencesDefaultValue.toString(),
                    preferencesDefaultValueType = preferencesDefaultValueType,
                )
            }

            accessorAnnotation == Set::class -> {
                if (preferencesKeyName == null || preferencesDefaultValueType == null) {
                    logger.logMissingFunctionAnnotationDataError(
                        interfaceName = interfaceName,
                        functionName = functionName
                    )
                    return null
                }

                generateSetFunctionUseCase(
                    functionName = functionName,
                    preferencesKeyName = preferencesKeyName,
                    preferencesDefaultValueType = preferencesDefaultValueType,
                )
            }

            functionalAnnotation == Clear::class -> generateClearFunctionUseCase(
                functionName = functionName,
            )

            else -> null
        }
    }
}
