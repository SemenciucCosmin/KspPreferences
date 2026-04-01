package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Get
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateGetFunctionDeclarationUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(
        interfaceName: String,
        function: KSFunctionDeclaration
    ): Boolean {
        val functionName = function.simpleName.asString()
        val valueTypeAnnotation = ValueTypeAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val preferencesDefaultValueType = getValueTypeAnnotationData(
            function = function
        ).third

        val declaration = function.returnType?.resolve()?.declaration
        val returnType = declaration?.simpleName?.asString()

        if (function.parameters.isNotEmpty()) {
            logger.logParameterOverloadError(
                interfaceName = interfaceName,
                functionName = functionName,
            )
        }

        if (preferencesDefaultValueType != returnType) {
            logger.logMismatchedReturnTypeError(
                interfaceName = interfaceName,
                functionName = functionName,
                accessorAnnotation = Get::class.simpleName ?: return false,
                valueTypeAnnotation = valueTypeAnnotation?.simpleName ?: return false,
                expectedReturnType = preferencesDefaultValueType ?: return false,
            )
        }

        return preferencesDefaultValueType == returnType && function.parameters.isEmpty()
    }
}
