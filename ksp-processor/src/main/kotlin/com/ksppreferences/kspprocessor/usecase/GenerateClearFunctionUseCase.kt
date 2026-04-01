package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental

internal class GenerateClearFunctionUseCase {

    @OptIn(KspExperimental::class)
    operator fun invoke(functionName: String): String {
        return buildString {
            appendLine(
                """ 
                |   override suspend fun $functionName() {
                |       context.dataStore.edit { it.clear() }
                |   }
                """.trimMargin()
            )
        }
    }
}
