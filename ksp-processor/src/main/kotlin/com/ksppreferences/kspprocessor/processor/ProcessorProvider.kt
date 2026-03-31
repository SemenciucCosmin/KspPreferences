package com.ksppreferences.kspprocessor.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.ksppreferences.kspprocessor.logger.Logger
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionAnnotationsUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateGetFlowFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateGetFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateSetFunctionDeclarationUseCase

internal class ProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logger = Logger(environment.logger)
        val validateFunctionAnnotationsUseCase by lazy {
            ValidateFunctionAnnotationsUseCase(logger)
        }

        val validateFunctionDeclarationUseCase by lazy {
            ValidateFunctionDeclarationUseCase(
                logger,
                ValidateGetFunctionDeclarationUseCase(logger),
                ValidateGetFlowFunctionDeclarationUseCase(logger),
                ValidateSetFunctionDeclarationUseCase(logger)
            )
        }

        return Processor(
            codeGenerator = environment.codeGenerator,
            validateFunctionAnnotationsUseCase = validateFunctionAnnotationsUseCase,
            validateFunctionDeclarationUseCase = validateFunctionDeclarationUseCase
        )
    }
}
