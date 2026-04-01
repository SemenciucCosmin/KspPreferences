package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.extension.ifNot
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateSetFunctionDeclarationUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(
        interfaceName: String,
        function: KSFunctionDeclaration
    ): Boolean {
        val functionName = function.simpleName.asString()
        val declaration = function.returnType?.resolve()?.declaration
        val returnType = declaration?.qualifiedName?.asString()
        val parameter = function.parameters.firstOrNull()
        val parameterType = parameter?.type?.resolve()?.declaration?.simpleName?.asString()
        val hasNoReturnType = returnType == Unit::class.qualifiedName

        val preferencesDefaultValueType = getValueTypeAnnotationData(function).third
        val isMatchingParameterType = parameterType == preferencesDefaultValueType

        return (hasNoReturnType && isMatchingParameterType).ifNot {
            logger.logUnnecessaryReturnTypeError(
                interfaceName = interfaceName,
                functionName = functionName,
                annotation = Set::class.simpleName ?: return@ifNot
            )
        }
    }
}
