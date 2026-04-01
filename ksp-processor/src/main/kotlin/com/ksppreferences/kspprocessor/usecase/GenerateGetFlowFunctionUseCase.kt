package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental

internal class GenerateGetFlowFunctionUseCase {

    @OptIn(KspExperimental::class)
    operator fun invoke(
        functionName: String,
        preferencesKeyName: String,
        preferencesDefaultValue: String,
        preferencesDefaultValueType: String,
    ): String {
        return buildString {
            appendLine(
                """ 
                |    override fun $functionName(): Flow<$preferencesDefaultValueType> {
                |        return context.dataStore.data.map { it[$preferencesKeyName] ?: $preferencesDefaultValue }
                |    }
                """.trimMargin()
            )
        }
    }
}
