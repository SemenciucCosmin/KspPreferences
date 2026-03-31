package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.extension.ifNot
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateSetFunctionDeclarationUseCase(
    private val logger: Logger,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()
        val declaration = function.returnType?.resolve()?.declaration
        val returnType = declaration?.qualifiedName?.asString()

        return (returnType == Unit::class.qualifiedName).ifNot {
            logger.logUnnecessaryReturnTypeError(functionName)
        }
    }
}
