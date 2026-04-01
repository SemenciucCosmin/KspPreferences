package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.Modifier
import com.ksppreferences.annotations.Clear
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.annotations.Set
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.annotations.FunctionalAnnotations
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateFunctionDeclarationUseCase(
    private val logger: Logger,
    private val validateGetFunctionDeclarationUseCase: ValidateGetFunctionDeclarationUseCase,
    private val validateGetFlowFunctionDeclarationUseCase: ValidateGetFlowFunctionDeclarationUseCase,
    private val validateSetFunctionDeclarationUseCase: ValidateSetFunctionDeclarationUseCase,
    private val validateClearFunctionDeclarationUseCase: ValidateClearFunctionDeclarationUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()
        val accessorAnnotation = AccessorAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val functionalAnnotations = FunctionalAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val isSuspendFunction = function.modifiers.contains(Modifier.SUSPEND)
        val isFlowFunction = accessorAnnotation == GetFlow::class
        if (isFlowFunction && isSuspendFunction) {
            logger.logNonSuspendingFunctionError(functionName)
            return false
        }

        return when {
            accessorAnnotation == Get::class -> validateGetFunctionDeclarationUseCase(function)
            accessorAnnotation == GetFlow::class -> validateGetFlowFunctionDeclarationUseCase(function)
            accessorAnnotation == Set::class -> validateSetFunctionDeclarationUseCase(function)
            functionalAnnotations == Clear::class -> validateClearFunctionDeclarationUseCase(function)
            else -> {
                logger.logMissingAnnotationError(functionName, AccessorAnnotations.allString)
                false
            }
        }
    }
}