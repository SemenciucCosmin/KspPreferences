package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.ksppreferences.kspprocessor.annotations.AccessorAnnotations
import com.ksppreferences.kspprocessor.annotations.FunctionalAnnotations
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
            logger.logConflictingAnnotationsError(functionName, AccessorAnnotations.allString)
            return false
        }

        if (isFunctionalAnnotationOverload) {
            logger.logConflictingAnnotationsError(functionName, FunctionalAnnotations.allString)
            return false
        }

        if (isValueTypeAnnotationOverload) {
            logger.logConflictingAnnotationsError(functionName, ValueTypeAnnotations.allString)
            return false
        }

        if (hasFunctionalAnnotation && (hasAccessorAnnotation || hasValueTypeAnnotation)) {
            logger.logConflictingAnnotationsError(functionName, FunctionalAnnotations.allString)
            return false
        }

        if (!hasFunctionalAnnotation && (!hasAccessorAnnotation && hasValueTypeAnnotation)) {
            logger.logMissingAnnotationError(functionName, AccessorAnnotations.allString)
            return false
        }

        if (!hasFunctionalAnnotation && (hasAccessorAnnotation && !hasValueTypeAnnotation)) {
            logger.logMissingAnnotationError(functionName, ValueTypeAnnotations.allString)
            return false
        }

        return true
    }

    companion object {
        private const val MAX_ANNOTATION_COUNT = 1
    }
}
