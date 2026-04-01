package com.ksppreferences.kspprocessor.logger

import com.google.devtools.ksp.processing.KSPLogger

internal class Logger(private val logger: KSPLogger) {

    fun logConflictingAnnotationsError(
        interfaceName: String,
        functionName: String,
        expectedAnnotations: String,
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName has multiple conflicting annotations.
            Please ensure that $functionName is annotated with only one of $expectedAnnotations.
            """.trimIndent()
        )
    }

    fun logMissingAnnotationError(
        interfaceName: String,
        functionName: String,
        expectedAnnotations: String,
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName has missing annotations.
            Please ensure that $functionName is annotated one of $expectedAnnotations.
            """.trimIndent()
        )
    }

    fun logUnnecessaryReturnTypeError(
        interfaceName: String,
        functionName: String,
        annotation: String,
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName is annotated with $annotation but has a return type.
            Please ensure that $functionName does not have a return type.
            """.trimIndent()
        )
    }

    fun logMismatchedReturnTypeError(
        interfaceName: String,
        functionName: String,
        accessorAnnotation: String,
        valueTypeAnnotation: String,
        expectedReturnType: String,
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName is annotated with $accessorAnnotation and $valueTypeAnnotation but does not return a $expectedReturnType.
            Please ensure that $functionName returns a $expectedReturnType.
            """.trimIndent()
        )
    }

    fun logNonSuspendingFunctionError(
        interfaceName: String,
        functionName: String
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName is not a suspending function.
            Please ensure that $functionName is a suspending function.
            """.trimIndent()
        )
    }

    fun logParameterOverloadError(
        interfaceName: String,
        functionName: String
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName surpassed the parameter count threshold.
            Please ensure that $functionName respects the parameters limits.
            """.trimIndent()
        )
    }

    fun logInvalidFunctionAnnotationError(
        interfaceName: String,
        functionName: String
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName has invalid annotations.
            Please ensure that $functionName is annotated correctly.
            """.trimIndent()
        )
    }

    fun logInvalidDeclarationAnnotationError(
        interfaceName: String,
        functionName: String
    ) {
        logger.error(
            """
            Function $functionName from interface $interfaceName has invalid declaration.
            Please ensure that $functionName declared correctly.
            """.trimIndent()
        )
    }

    fun logMisingInterfaceFileError(interfaceName: String) {
        logger.error(
            """
            Interface file $interfaceName could not be found and implementation cannot be generated.
            """.trimIndent()
        )
    }

    fun logMisingPreferencesNameError(interfaceName: String) {
        logger.error(
            """
            Preferences name for interface $interfaceName could not be found and implementation cannot be generated.
            Please ensure that $interfaceName is annotated with @Preferences and has a valid name parameter.
            """.trimIndent()
        )
    }

    fun logMissingFunctionAnnotationDataError(
        interfaceName: String,
        functionName: String
    ) {
        logger.error(
            """
            Annotation data for function $functionName from interface $interfaceName could not be found and implementation cannot be generated.
            Please ensure that $functionName is annotated correctly and has valid parameters.
            """.trimIndent()
        )
    }

    fun logMissingInterfaceAnnotationDataError(interfaceName: String) {
        logger.error(
            """
            Annotation data for interface $interfaceName could not be found and implementation cannot be generated.
            Please ensure that $interfaceName is annotated correctly and has valid parameters.
            """.trimIndent()
        )
    }
}
