package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.annotations.ValueTypeAnnotations
import com.ksppreferences.kspprocessor.logger.Logger

internal class ValidateFunctionAnnotationsUseCase(
    private val logger: Logger,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(function: KSFunctionDeclaration): Boolean {
        val functionName = function.simpleName.asString()

        val accessorAnnotationsCount = AccessorAnnotations.all.count {
            function.isAnnotationPresent(it)
        }

        val valueTypeAnnotationsCount = ValueTypeAnnotations.all.count {
            function.isAnnotationPresent(it)
        }

        val hasValidAccessorAnnotation = accessorAnnotationsCount <= MAX_ANNOTATION_COUNT
        val hasValidValueTypeAnnotation = valueTypeAnnotationsCount <= MAX_ANNOTATION_COUNT
        val hasAccessorAnnotation = accessorAnnotationsCount == MAX_ANNOTATION_COUNT
        val hasValueTypeAnnotation = valueTypeAnnotationsCount == MAX_ANNOTATION_COUNT

        if (!hasValidAccessorAnnotation) {
            logger.logConflictingAnnotationsError(functionName, AccessorAnnotations.allString)
            return false
        }

        if (!hasValidValueTypeAnnotation) {
            logger.logConflictingAnnotationsError(functionName, ValueTypeAnnotations.allString)
            return false
        }

        if (!hasAccessorAnnotation && hasValueTypeAnnotation) {
            logger.logMissingAnnotationError(functionName, AccessorAnnotations.allString)
            return false
        }

        if (hasAccessorAnnotation && !hasValueTypeAnnotation) {
            logger.logMissingAnnotationError(functionName, ValueTypeAnnotations.allString)
            return false
        }

        return true
    }

    companion object {
        private const val MAX_ANNOTATION_COUNT = 1
    }
}
