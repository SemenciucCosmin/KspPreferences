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

/**
 * Routes a validated function declaration to the correct type-specific validator based on
 * its accessor or functional annotation.
 *
 * This use case is called only after [ValidateFunctionAnnotationsUseCase] has already
 * confirmed that the annotation composition is sound, so each branch can focus solely on
 * declaration-level rules (return type, parameter count, `suspend` modifier, etc.).
 */
internal class ValidateFunctionDeclarationUseCase(
    private val logger: Logger,
    private val validateGetFunctionDeclarationUseCase: ValidateGetFunctionDeclarationUseCase,
    private val validateGetFlowFunctionDeclarationUseCase: ValidateGetFlowFunctionDeclarationUseCase,
    private val validateSetFunctionDeclarationUseCase: ValidateSetFunctionDeclarationUseCase,
    private val validateClearFunctionDeclarationUseCase: ValidateClearFunctionDeclarationUseCase,
) {

    /**
     * Validates the declaration of [function] by delegating to the appropriate
     * type-specific validator.
     *
     * @param interfaceName The simple name of the enclosing interface (used in error messages).
     * @param function      The KSP declaration of the function to validate.
     * @return `true` if the declaration is valid; `false` otherwise.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(
        interfaceName: String,
        function: KSFunctionDeclaration,
    ): Boolean {
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
            logger.logNonSuspendingFunctionError(
                interfaceName = interfaceName,
                functionName = functionName
            )
            return false
        }

        return when {
            accessorAnnotation == Get::class -> validateGetFunctionDeclarationUseCase(
                interfaceName = interfaceName,
                function = function
            )

            accessorAnnotation == GetFlow::class -> validateGetFlowFunctionDeclarationUseCase(
                interfaceName = interfaceName,
                function = function
            )

            accessorAnnotation == Set::class -> validateSetFunctionDeclarationUseCase(
                interfaceName = interfaceName,
                function = function
            )

            functionalAnnotations == Clear::class -> validateClearFunctionDeclarationUseCase(
                interfaceName = interfaceName,
                function = function
            )

            else -> {
                logger.logMissingAnnotationError(
                    interfaceName = interfaceName,
                    functionName = functionName,
                    expectedAnnotations = AccessorAnnotations.allString
                )
                false
            }
        }
    }
}