package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.Set
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateFunctionDeclarationUseCase(
    private val logger: Logger,
    private val validateGetFunctionDeclarationUseCase: ValidateGetFunctionDeclarationUseCase,
    private val validateGetFlowFunctionDeclarationUseCase: ValidateGetFlowFunctionDeclarationUseCase,
    private val validateSetFunctionDeclarationUseCase: ValidateSetFunctionDeclarationUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()
        val accessorAnnotation = AccessorAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        return when (accessorAnnotation) {
            Get::class -> validateGetFunctionDeclarationUseCase(function)
            GetFlow::class -> validateGetFlowFunctionDeclarationUseCase(function)
            Set::class -> validateSetFunctionDeclarationUseCase(function)
            else -> {
                logger.logMissingAnnotationError(functionName, AccessorAnnotations.allString)
                false
            }
        }
    }
}