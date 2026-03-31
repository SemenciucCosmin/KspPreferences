package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.Set
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations

internal class GenerateFunctionUseCase(
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
    private val generateGetFunctionUseCase: GenerateGetFunctionUseCase,
    private val generateGetFlowFunctionUseCase: GenerateGetFlowFunctionUseCase,
    private val generateSetFunctionUseCase: GenerateSetFunctionUseCase,
    private val generateClearFunctionUseCase: GenerateClearFunctionUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): String? {
        val functionName = function.simpleName.asString()
        val accessorAnnotation = AccessorAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val (
            preferencesKeyName,
            preferencesDefaultValue,
            preferencesDefaultValueType,
        ) = getValueTypeAnnotationData(function) ?: return null

        return when (accessorAnnotation) {
            Get::class -> {
                generateGetFunctionUseCase(
                    functionName = functionName,
                    preferencesKeyName = preferencesKeyName,
                    preferencesDefaultValue = preferencesDefaultValue.toString(),
                    preferencesDefaultValueType = preferencesDefaultValueType ?: return null,
                )
            }

            GetFlow::class -> generateGetFlowFunctionUseCase(
                functionName = functionName,
                preferencesKeyName = preferencesKeyName,
                preferencesDefaultValue = preferencesDefaultValue.toString(),
                preferencesDefaultValueType = preferencesDefaultValueType ?: return null,
            )

            Set::class -> generateSetFunctionUseCase(
                functionName = functionName,
                preferencesKeyName = preferencesKeyName,
                preferencesDefaultValueType = preferencesDefaultValueType ?: return null,
            )

            else -> null
        }
    }
}
