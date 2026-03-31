package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.logger.Logger
import kotlinx.coroutines.flow.firstOrNull
import kotlin.sequences.forEach

internal class GenerateClearFunctionUseCase {

    @OptIn(KspExperimental::class)
    operator fun invoke(
        functionName: String,
        returnType: String,
        preferencesKey: String,
    ): String {
        return buildString {
            appendLine(
                """ 
                |   override suspend fun clear() {
                |       context.dataStore.edit { it.clear() }
                |   }
                """.trimMargin()
            )
        }
    }
}
