package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.GetFlow
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.extension.ifNot
import com.ksppreferences.kspprocessor.logger.Logger
import kotlinx.coroutines.flow.Flow

internal class ValidateGetFlowFunctionDeclarationUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()
        val valueTypeAnnotation = ValueTypeAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        } ?: return false

        val preferencesDefaultValueType = getValueTypeAnnotationData(
            function = function
        ).third ?: return false

        val arguments = function.returnType?.resolve()?.arguments
        val declaration = function.returnType?.resolve()?.declaration
        val innerType = arguments?.firstOrNull()?.type?.resolve()
        val outerType = declaration?.simpleName?.asString()
        val returnType = innerType?.declaration?.simpleName?.asString()

        if (function.parameters.isNotEmpty()) {
            logger.logParameterOverloadError(
                functionName = functionName,
            )
        }

        if (preferencesDefaultValueType != returnType || outerType != Flow::class.simpleName) {
            logger.logMismatchedReturnTypeError(
                functionName = functionName,
                accessorAnnotation = GetFlow::class.simpleName ?: return false,
                valueTypeAnnotation = valueTypeAnnotation.simpleName ?: return false,
                expectedReturnType = "Flow<$preferencesDefaultValueType>",
            )
        }

        return preferencesDefaultValueType == returnType && function.parameters.isEmpty()
    }
}
