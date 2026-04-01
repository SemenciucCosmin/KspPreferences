package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateInterfaceUseCase(
    private val logger: Logger,
    private val validateFunctionAnnotationsUseCase: ValidateFunctionAnnotationsUseCase,
    private val validateFunctionDeclarationUseCase: ValidateFunctionDeclarationUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): Boolean {
        val interfaceName = interfaceDeclaration.simpleName.asString()
        return interfaceDeclaration.getDeclaredFunctions().all { function ->
            val functionName = function.simpleName.asString()
            val isFunctionAnnotationValid = validateFunctionAnnotationsUseCase(
                interfaceName = interfaceName,
                function = function
            )

            val isFunctionDeclarationValid = validateFunctionDeclarationUseCase(
                interfaceName = interfaceName,
                function = function
            )

            if (!isFunctionAnnotationValid) {
                logger.logInvalidFunctionAnnotationError(
                    interfaceName = interfaceName,
                    functionName = functionName
                )
            }

            if (!isFunctionDeclarationValid) {
                logger.logInvalidDeclarationAnnotationError(
                    interfaceName = interfaceName,
                    functionName = functionName
                )
            }

            isFunctionDeclarationValid && isFunctionAnnotationValid
        }
    }
}
