package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.extension.ifNot
import com.ksppreferences.kspprocessor.extension.isTypeOf
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateSetFunctionDeclarationUseCase(
    private val logger: Logger,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()
        return (function.returnType.isTypeOf(Unit::class)).ifNot {
            logger.logUnnecessaryReturnTypeError(functionName)
        }
    }
}
