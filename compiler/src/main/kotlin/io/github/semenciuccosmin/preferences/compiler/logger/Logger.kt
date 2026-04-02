package io.github.semenciuccosmin.preferences.compiler.logger

import com.google.devtools.ksp.processing.KSPLogger

/**
 * Thin wrapper around [KSPLogger] that provides structured, human-readable error messages
 * for all validation and code-generation failures produced by the KSP processor.
 *
 * Every method delegates to [KSPLogger.error], which causes the KSP build step to fail and
 * surfaces the message in the IDE / build output alongside the relevant source location.
 */
internal class Logger(private val logger: KSPLogger) {

    /**
     * Reports that a function carries more than one annotation from the same annotation group,
     * creating an ambiguous operation.
     *
     * @param interfaceName    The simple name of the enclosing interface.
     * @param functionName     The simple name of the offending function.
     * @param expectedAnnotations Human-readable list of the mutually exclusive annotations.
     */
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

    /**
     * Reports that a function is missing a required annotation from the given group.
     *
     * @param interfaceName    The simple name of the enclosing interface.
     * @param functionName     The simple name of the offending function.
     * @param expectedAnnotations Human-readable list of the annotations, one of which is required.
     */
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

    /**
     * Reports that a function annotated with [io.github.semenciuccosmin.preferences.annotations.Set] or
     * [io.github.semenciuccosmin.preferences.annotations.Clear] declares a non-Unit return type.
     *
     * @param interfaceName The simple name of the enclosing interface.
     * @param functionName  The simple name of the offending function.
     * @param annotation    The simple name of the annotation that forbids a return type.
     */
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

    /**
     * Reports that the return type of a [io.github.semenciuccosmin.preferences.annotations.Get] or
     * [io.github.semenciuccosmin.preferences.annotations.GetFlow] function does not match the type declared
     * by its value-type annotation.
     *
     * @param interfaceName      The simple name of the enclosing interface.
     * @param functionName       The simple name of the offending function.
     * @param accessorAnnotation The simple name of the accessor annotation ([Get]/[GetFlow]).
     * @param valueTypeAnnotation The simple name of the value-type annotation.
     * @param expectedReturnType Human-readable description of the required return type.
     */
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

    /**
     * Reports that a [io.github.semenciuccosmin.preferences.annotations.GetFlow] function is mistakenly marked
     * `suspend`. Flow-returning functions must not be suspending.
     *
     * @param interfaceName The simple name of the enclosing interface.
     * @param functionName  The simple name of the offending function.
     */
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

    /**
     * Reports that a function declares more parameters than are allowed for its annotation.
     *
     * @param interfaceName The simple name of the enclosing interface.
     * @param functionName  The simple name of the offending function.
     */
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

    /**
     * Reports that a function's annotations failed validation (summary-level error, logged
     * after a more specific error has already been emitted).
     *
     * @param interfaceName The simple name of the enclosing interface.
     * @param functionName  The simple name of the offending function.
     */
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

    /**
     * Reports that a function's declaration failed validation (summary-level error, logged
     * after a more specific error has already been emitted).
     *
     * @param interfaceName The simple name of the enclosing interface.
     * @param functionName  The simple name of the offending function.
     */
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

    /**
     * Reports that the source file containing the annotated interface could not be resolved,
     * preventing the processor from generating the implementation.
     *
     * @param interfaceName The simple name of the interface whose file is missing.
     */
    fun logMisingInterfaceFileError(interfaceName: String) {
        logger.error(
            """
            Interface file $interfaceName could not be found and implementation cannot be generated.
            """.trimIndent()
        )
    }

    /**
     * Reports that the [io.github.semenciuccosmin.preferences.annotations.Preferences.name] parameter could not
     * be read from the annotation, preventing the processor from naming the DataStore file.
     *
     * @param interfaceName The simple name of the annotated interface.
     */
    fun logMisingPreferencesNameError(interfaceName: String) {
        logger.error(
            """
            Preferences name for interface $interfaceName could not be found and implementation cannot be generated.
            Please ensure that $interfaceName is annotated with @Preferences and has a valid name parameter.
            """.trimIndent()
        )
    }

    /**
     * Reports that the key or default-value data could not be read from a function's
     * value-type annotation, preventing code generation for that function.
     *
     * @param interfaceName The simple name of the enclosing interface.
     * @param functionName  The simple name of the offending function.
     */
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

    /**
     * Reports that no valid preference pairs could be collected from the interface, making
     * it impossible to generate a companion object with the required preference keys.
     *
     * @param interfaceName The simple name of the annotated interface.
     */
    fun logMissingInterfaceAnnotationDataError(interfaceName: String) {
        logger.error(
            """
            Annotation data for interface $interfaceName could not be found and implementation cannot be generated.
            Please ensure that $interfaceName is annotated correctly and has valid parameters.
            """.trimIndent()
        )
    }
}
