package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.semenciuccosmin.preferences.compiler.annotations.AccessorAnnotations
import io.github.semenciuccosmin.preferences.compiler.annotations.FunctionalAnnotations
import io.github.semenciuccosmin.preferences.compiler.annotations.ValueTypeAnnotations
import io.github.semenciuccosmin.preferences.compiler.logger.Logger

/**
 * Validates the annotation composition of a single interface function.
 *
 * The rules enforced are:
 * - At most one accessor annotation ([AccessorAnnotations]).
 * - At most one functional annotation ([FunctionalAnnotations]).
 * - At most one value-type annotation ([ValueTypeAnnotations]).
 * - A functional annotation may not coexist with an accessor or value-type annotation.
 * - An accessor annotation without a value-type annotation is invalid, and vice-versa.
 *
 * All violations are reported via [Logger] without throwing; `false` is returned so the
 * processor can collect and display every problem in a single build pass.
 */
internal class ValidateFunctionAnnotationsUseCase(
    private val logger: Logger,
) {

    /**
     * Validates the annotations on [function].
     *
     * @param interfaceName The simple name of the enclosing interface (used in error messages).
     * @param function      The KSP declaration of the function to validate.
     * @return `true` if all annotation constraints are satisfied; `false` otherwise.
     */
    @Suppress("ReturnCount")
    @OptIn(KspExperimental::class)
    operator fun invoke(
        interfaceName: String,
        function: KSFunctionDeclaration
    ): Boolean {
        val functionName = function.simpleName.asString()

        val accessorAnnotationsCount = AccessorAnnotations.all.count {
            function.isAnnotationPresent(it)
        }

        val functionalAnnotationsCount = FunctionalAnnotations.all.count {
            function.isAnnotationPresent(it)
        }

        val valueTypeAnnotationsCount = ValueTypeAnnotations.all.count {
            function.isAnnotationPresent(it)
        }

        val isAccessorAnnotationOverload = accessorAnnotationsCount > MAX_ANNOTATION_COUNT
        val isFunctionalAnnotationOverload = functionalAnnotationsCount > MAX_ANNOTATION_COUNT
        val isValueTypeAnnotationOverload = valueTypeAnnotationsCount > MAX_ANNOTATION_COUNT
        val hasAccessorAnnotation = accessorAnnotationsCount == MAX_ANNOTATION_COUNT
        val hasFunctionalAnnotation = functionalAnnotationsCount == MAX_ANNOTATION_COUNT
        val hasValueTypeAnnotation = valueTypeAnnotationsCount == MAX_ANNOTATION_COUNT

        if (isAccessorAnnotationOverload) {
            logger.logConflictingAnnotationsError(
                interfaceName = interfaceName,
                functionName = functionName,
                expectedAnnotations = AccessorAnnotations.allString
            )
            return false
        }

        if (isFunctionalAnnotationOverload) {
            logger.logConflictingAnnotationsError(
                interfaceName = interfaceName,
                functionName = functionName,
                expectedAnnotations = FunctionalAnnotations.allString
            )
            return false
        }

        if (isValueTypeAnnotationOverload) {
            logger.logConflictingAnnotationsError(
                interfaceName = interfaceName,
                functionName = functionName,
                expectedAnnotations = ValueTypeAnnotations.allString
            )
            return false
        }

        if (hasFunctionalAnnotation && (hasAccessorAnnotation || hasValueTypeAnnotation)) {
            logger.logConflictingAnnotationsError(
                interfaceName = interfaceName,
                functionName = functionName,
                expectedAnnotations = FunctionalAnnotations.allString
            )
            return false
        }

        if (!hasFunctionalAnnotation && (!hasAccessorAnnotation && hasValueTypeAnnotation)) {
            logger.logMissingAnnotationError(
                interfaceName = interfaceName,
                functionName = functionName,
                expectedAnnotations = AccessorAnnotations.allString
            )
            return false
        }

        if (!hasFunctionalAnnotation && (hasAccessorAnnotation && !hasValueTypeAnnotation)) {
            logger.logMissingAnnotationError(
                interfaceName = interfaceName,
                functionName = functionName,
                expectedAnnotations = ValueTypeAnnotations.allString
            )
            return false
        }

        return true
    }

    companion object {
        private const val MAX_ANNOTATION_COUNT = 1
    }
}
