package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.github.semenciuccosmin.preferences.compiler.logger.Logger

/**
 * Validates every declared function in a [io.github.semenciuccosmin.preferences.annotations.Preferences]-annotated
 * interface, combining annotation composition checks and declaration-level checks.
 *
 * Processing is not short-circuited on the first failure: all functions are visited so that
 * every error can be reported to the developer in a single build pass.
 *
 * @see ValidateFunctionAnnotationsUseCase
 * @see ValidateFunctionDeclarationUseCase
 */
internal class ValidateInterfaceUseCase(
    private val logger: Logger,
    private val validateFunctionAnnotationsUseCase: ValidateFunctionAnnotationsUseCase,
    private val validateFunctionDeclarationUseCase: ValidateFunctionDeclarationUseCase,
) {

    /**
     * Validates all declared functions in [interfaceDeclaration].
     *
     * @param interfaceDeclaration The KSP declaration of the annotated interface.
     * @return `true` only when every function in the interface passes both annotation and
     *         declaration validation; `false` if any function is invalid.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration): Boolean {
        val interfaceName = interfaceDeclaration.simpleName.asString()
        return interfaceDeclaration.getDeclaredFunctions().all { function ->
            val functionName = function.simpleName.asString()
            val isFunctionAnnotationValid = validateFunctionAnnotationsUseCase(
                interfaceName = interfaceName,
                function = function
            )

            val isFunctionDeclarationValid = validateFunctionDeclarationUseCase(
                interfaceName = interfaceName,
                function = function
            )

            if (!isFunctionAnnotationValid) {
                logger.logInvalidFunctionAnnotationError(
                    interfaceName = interfaceName,
                    functionName = functionName
                )
            }

            if (!isFunctionDeclarationValid) {
                logger.logInvalidDeclarationAnnotationError(
                    interfaceName = interfaceName,
                    functionName = functionName
                )
            }

            isFunctionDeclarationValid && isFunctionAnnotationValid
        }
    }
}
