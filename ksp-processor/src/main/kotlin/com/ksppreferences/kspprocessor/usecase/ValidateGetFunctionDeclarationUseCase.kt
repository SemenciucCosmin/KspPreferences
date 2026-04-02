package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.annotations.Get
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.logger.Logger

/**
 * Validates that a function annotated with [Get] satisfies the required declaration
 * constraints.
 *
 * A valid `@Get` function must:
 * - Return the type that matches the value type declared by the paired value-type annotation.
 * - Declare no parameters.
 *
 * Validation errors are reported via [Logger] but do not throw; the method returns `false`
 * instead, allowing the processor to continue and surface all errors in one build pass.
 */
internal class ValidateGetFunctionDeclarationUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
) {

    /**
     * Validates the declaration of a [Get]-annotated function.
     *
     * @param interfaceName The simple name of the enclosing interface (used in error messages).
     * @param function      The KSP declaration of the function to validate.
     * @return `true` if the function is valid; `false` otherwise.
     */
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
