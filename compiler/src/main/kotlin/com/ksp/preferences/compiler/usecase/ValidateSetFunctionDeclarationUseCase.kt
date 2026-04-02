package com.ksp.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksp.preferences.compiler.extension.ifNot
import com.ksp.preferences.compiler.logger.Logger

/**
 * Validates that a function annotated with [com.ksppreferences.annotations.Set] satisfies
 * the required declaration constraints.
 *
 * A valid `@Set` function must:
 * - Return `Unit`.
 * - Declare exactly one parameter whose type matches the value type declared by the paired
 *   value-type annotation.
 *
 * Validation errors are reported via [Logger] and the [ifNot] helper; the method returns
 * `false` on failure without throwing.
 */
internal class ValidateSetFunctionDeclarationUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
) {

    /**
     * Validates the declaration of a [com.ksppreferences.annotations.Set]-annotated function.
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
