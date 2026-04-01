package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Clear
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateClearFunctionDeclarationUseCase(
    private val logger: Logger,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(
        interfaceName: String,
        function: KSFunctionDeclaration
    ): Boolean {
        val functionName = function.simpleName.asString()
        val declaration = function.returnType?.resolve()?.declaration
        val returnType = declaration?.qualifiedName?.asString()

        if (returnType != Unit::class.qualifiedName) {
            logger.logUnnecessaryReturnTypeError(
                interfaceName = interfaceName,
                functionName = functionName,
                annotation = Clear::class.simpleName ?: return false
            )
        }

        if (!function.parameters.isEmpty()) {
            logger.logParameterOverloadError(
                interfaceName = interfaceName,
                functionName = functionName,
            )
        }

        return returnType == Unit::class.qualifiedName && function.parameters.isEmpty()
    }
}
