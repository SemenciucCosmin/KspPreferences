package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Get
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.extension.ifNot
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateGetFunctionDeclarationUseCase(
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
        )?.third ?: return false

        val declaration = function.returnType?.resolve()?.declaration
        val returnType = declaration?.simpleName?.asString() ?: return false

        return (preferencesDefaultValueType == returnType).ifNot {
            logger.logMismatchedReturnTypeError(
                functionName = functionName,
                accessorAnnotation = Get::class.simpleName ?: return@ifNot,
                valueTypeAnnotation = valueTypeAnnotation.simpleName ?: return@ifNot,
                expectedReturnType = preferencesDefaultValueType,
            )
        }
    }
}
