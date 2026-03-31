package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental

internal class GenerateSetFunctionUseCase {

    @OptIn(KspExperimental::class)
    operator fun invoke(
        functionName: String,
        preferencesKeyName: String,
        preferencesDefaultValueType: String,
    ): String {
        return buildString {
            appendLine(
                """ 
                |    override suspend fun $functionName(value: $preferencesDefaultValueType) {
                |        context.dataStore.edit { it[$preferencesKeyName] = value }
                |    }
                """.trimMargin()
            )
        }
    }
}
