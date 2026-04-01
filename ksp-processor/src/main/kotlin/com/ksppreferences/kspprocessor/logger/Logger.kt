package com.ksppreferences.kspprocessor.logger

import com.google.devtools.ksp.processing.KSPLogger

internal class Logger(private val logger: KSPLogger) {

    fun logConflictingAnnotationsError(
        functionName: String,
        expectedAnnotations: String,
    ) {
        logger.error(
            """
            Function $functionName has multiple conflicting annotations.
            Please ensure that $functionName is annotated with only one of $expectedAnnotations.
            """.trimIndent()
        )
    }

    fun logMissingAnnotationError(
        functionName: String,
        expectedAnnotations: String,
    ) {
        logger.error(
            """
            Function $functionName has missing annotations.
            Please ensure that $functionName is annotated one of $expectedAnnotations.
            """.trimIndent()
        )
    }

    fun logUnnecessaryReturnTypeError(
        functionName: String,
        annotation: String
    ) {
        logger.error(
            """
            Function $functionName is annotated with $annotation but has a return type.
            Please ensure that $functionName does not have a return type.
            """.trimIndent()
        )
    }

    fun logMismatchedReturnTypeError(
        functionName: String,
        accessorAnnotation: String,
        valueTypeAnnotation: String,
        expectedReturnType: String,
    ) {
        logger.error(
            """
            Function $functionName is annotated with $accessorAnnotation and $valueTypeAnnotation but does not return a $expectedReturnType.
            Please ensure that $functionName returns a $expectedReturnType.
            """.trimIndent()
        )
    }

    fun logNonSuspendingFunctionError(functionName: String) {
        logger.error(
            """
            Function $functionName is not a suspending function.
            Please ensure that $functionName is a suspending function.
            """.trimIndent()
        )
    }

    fun logParameterOverloadError(functionName: String) {
        logger.error(
            """
            Function $functionName surpassed the parameter count threshold.
            Please ensure that $functionName respects the parameters limits.
            """.trimIndent()
        )
    }
}
