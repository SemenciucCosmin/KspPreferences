package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.logger.Logger
import kotlin.sequences.forEach

internal class ValidateInterfaceUseCase(
    private val validateFunctionAnnotationsUseCase: ValidateFunctionAnnotationsUseCase,
    private val validateFunctionDeclarationUseCase: ValidateFunctionDeclarationUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): Boolean {
        return interfaceDeclaration.getDeclaredFunctions().all { function ->
            val isFunctionAnnotationValid = validateFunctionAnnotationsUseCase(function)
            val isFunctionDeclarationValid = validateFunctionDeclarationUseCase(function)
            isFunctionDeclarationValid && isFunctionAnnotationValid
        }
    }
}
