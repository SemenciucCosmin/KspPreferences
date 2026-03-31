package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental

internal class GenerateGetFunctionUseCase {

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
                |    override suspend fun $functionName(): $preferencesDefaultValueType {
                |        val preferences = context.dataStore.data.firstOrNull()
                |        return preferences?.get($preferencesKeyName) ?: $preferencesDefaultValue
                |    }
                """.trimMargin()
            )
        }
    }
}
