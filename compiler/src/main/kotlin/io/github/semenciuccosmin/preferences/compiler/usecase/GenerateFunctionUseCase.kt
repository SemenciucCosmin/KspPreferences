package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.isAnnotationPresent
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import io.github.semenciuccosmin.preferences.annotations.Clear
import io.github.semenciuccosmin.preferences.annotations.Get
import io.github.semenciuccosmin.preferences.annotations.GetFlow
import io.github.semenciuccosmin.preferences.annotations.Set
import io.github.semenciuccosmin.preferences.compiler.annotations.AccessorAnnotations
import io.github.semenciuccosmin.preferences.compiler.annotations.FunctionalAnnotations
import io.github.semenciuccosmin.preferences.compiler.logger.Logger

/**
 * Dispatches code generation for a single interface function to the appropriate
 * specialised generator based on its accessor or functional annotation.
 *
 * Returns `null` when the function carries no recognised annotation or when required
 * annotation data is missing, in which case a detailed error is logged via [Logger].
 */
internal class GenerateFunctionUseCase(
    private val logger: Logger,
    private val getValueTypeAnnotationData: GetValueTypeAnnotationData,
    private val generateGetFunctionUseCase: GenerateGetFunctionUseCase,
    private val generateGetFlowFunctionUseCase: GenerateGetFlowFunctionUseCase,
    private val generateSetFunctionUseCase: GenerateSetFunctionUseCase,
    private val generateClearFunctionUseCase: GenerateClearFunctionUseCase,
) {

    /**
     * Generates Kotlin source code for a single interface function override.
     *
     * @param interfaceName The simple name of the enclosing interface (used for error messages).
     * @param function      The KSP declaration of the function to generate.
     * @return The Kotlin source string for the override, or `null` if generation failed.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(
        interfaceName: String,
        function: KSFunctionDeclaration
    ): String? {
        val functionName = function.simpleName.asString()
        val accessorAnnotation = AccessorAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val functionalAnnotation = FunctionalAnnotations.all.firstOrNull {
            function.isAnnotationPresent(it)
        }

        val (
            preferencesKeyName,
            preferencesDefaultValue,
            preferencesDefaultValueType,
        ) = getValueTypeAnnotationData(function)

        return when {
            accessorAnnotation == Get::class -> {
                if (preferencesKeyName == null || preferencesDefaultValueType == null) {
                    logger.logMissingFunctionAnnotationDataError(
                        interfaceName = interfaceName,
                        functionName = functionName
                    )
                    return null
                }

                generateGetFunctionUseCase(
                    functionName = functionName,
                    preferencesKeyName = preferencesKeyName,
                    preferencesDefaultValue = preferencesDefaultValue.toString(),
                    preferencesDefaultValueType = preferencesDefaultValueType,
                )
            }

            accessorAnnotation == GetFlow::class -> {
                if (preferencesKeyName == null || preferencesDefaultValueType == null) {
                    logger.logMissingFunctionAnnotationDataError(
                        interfaceName = interfaceName,
                        functionName = functionName
                    )
                    return null
                }

                generateGetFlowFunctionUseCase(
                    functionName = functionName,
                    preferencesKeyName = preferencesKeyName,
                    preferencesDefaultValue = preferencesDefaultValue.toString(),
                    preferencesDefaultValueType = preferencesDefaultValueType,
                )
            }

            accessorAnnotation == Set::class -> {
                if (preferencesKeyName == null || preferencesDefaultValueType == null) {
                    logger.logMissingFunctionAnnotationDataError(
                        interfaceName = interfaceName,
                        functionName = functionName
                    )
                    return null
                }

                generateSetFunctionUseCase(
                    functionName = functionName,
                    preferencesKeyName = preferencesKeyName,
                    preferencesDefaultValueType = preferencesDefaultValueType,
                )
            }

            functionalAnnotation == Clear::class -> generateClearFunctionUseCase(
                functionName = functionName,
            )

            else -> null
        }
    }
}
