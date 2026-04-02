package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.semenciuccosmin.preferences.annotations.GetFlow
import io.github.semenciuccosmin.preferences.compiler.annotations.ValueTypeAnnotations
import io.github.semenciuccosmin.preferences.compiler.logger.Logger
import kotlinx.coroutines.flow.Flow

/**
 * Validates that a function annotated with [GetFlow] satisfies the required declaration
 * constraints.
 *
 * A valid `@GetFlow` function must:
 * - **Not** be `suspend` (the `Flow` itself is cold and non-blocking at the call site).
 * - Return `Flow<T>` where `T` exactly matches the value type declared by the paired
 *   value-type annotation.
 * - Declare no parameters.
 *
 * Validation errors are reported via [Logger] but do not throw; the method returns `false`
 * instead, allowing the processor to continue and surface all errors in one build pass.
 */
internal class ValidateGetFlowFunctionDeclarationUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
) {

    /**
     * Validates the declaration of a [GetFlow]-annotated function.
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

        val arguments = function.returnType?.resolve()?.arguments
        val declaration = function.returnType?.resolve()?.declaration
        val innerType = arguments?.firstOrNull()?.type?.resolve()
        val outerType = declaration?.simpleName?.asString()
        val returnType = innerType?.declaration?.simpleName?.asString()

        if (function.parameters.isNotEmpty()) {
            logger.logParameterOverloadError(
                interfaceName = interfaceName,
                functionName = functionName,
            )
        }

        if (preferencesDefaultValueType != returnType || outerType != Flow::class.simpleName) {
            logger.logMismatchedReturnTypeError(
                interfaceName = interfaceName,
                functionName = functionName,
                accessorAnnotation = GetFlow::class.simpleName ?: return false,
                valueTypeAnnotation = valueTypeAnnotation?.simpleName ?: return false,
                expectedReturnType = "Flow<$preferencesDefaultValueType>",
            )
        }

        return preferencesDefaultValueType == returnType && function.parameters.isEmpty()
    }
}
