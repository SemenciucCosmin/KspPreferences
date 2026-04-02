package com.ksp.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksp.preferences.annotations.Clear
import com.ksp.preferences.compiler.logger.Logger

/**
 * Validates that a function annotated with [Clear] satisfies the required declaration
 * constraints.
 *
 * A valid `@Clear` function must:
 * - Return `Unit`.
 * - Declare no parameters.
 *
 * Validation errors are reported via [Logger] but do not throw; the method returns `false`
 * instead, allowing the processor to continue and surface all errors in one build pass.
 */
internal class ValidateClearFunctionDeclarationUseCase(
    private val logger: Logger,
) {

    /**
     * Validates the declaration of a [Clear]-annotated function.
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
