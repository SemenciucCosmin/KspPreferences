package com.ksppreferences.kspprocessor.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.ksppreferences.kspprocessor.logger.Logger
import com.ksppreferences.kspprocessor.usecase.GenerateClearFunctionUseCase
import com.ksppreferences.kspprocessor.usecase.GenerateCompanionObjectUseCase
import com.ksppreferences.kspprocessor.usecase.GenerateFunctionUseCase
import com.ksppreferences.kspprocessor.usecase.GenerateGetFlowFunctionUseCase
import com.ksppreferences.kspprocessor.usecase.GenerateGetFunctionUseCase
import com.ksppreferences.kspprocessor.usecase.GenerateImplementationUseCase
import com.ksppreferences.kspprocessor.usecase.GenerateImportsUseCase
import com.ksppreferences.kspprocessor.usecase.GenerateSetFunctionUseCase
import com.ksppreferences.kspprocessor.usecase.GetPreferencesNameUseCase
import com.ksppreferences.kspprocessor.usecase.GetValueTypeAnnotationData
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionAnnotationsUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateGetFlowFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateGetFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateInterfaceUseCase
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
                ValidateGetFunctionDeclarationUseCase(logger, GetValueTypeAnnotationData()),
                ValidateGetFlowFunctionDeclarationUseCase(logger, GetValueTypeAnnotationData()),
                ValidateSetFunctionDeclarationUseCase(logger)
            )
        }

        val validateInterfaceUseCase by lazy {
            ValidateInterfaceUseCase(
                validateFunctionAnnotationsUseCase,
                validateFunctionDeclarationUseCase,
            )
        }

        val generateFunctionUseCase by lazy {
            GenerateFunctionUseCase(
                generateGetFunctionUseCase = GenerateGetFunctionUseCase(),
                generateGetFlowFunctionUseCase = GenerateGetFlowFunctionUseCase(),
                generateSetFunctionUseCase = GenerateSetFunctionUseCase(),
                generateClearFunctionUseCase = GenerateClearFunctionUseCase(),
                getValueTypeAnnotationData = GetValueTypeAnnotationData()
            )
        }

        val generateCompanionObjectUseCase by lazy {
            GenerateCompanionObjectUseCase(
                getValueTypeAnnotationData = GetValueTypeAnnotationData(),
                getPreferencesNameUseCase = GetPreferencesNameUseCase()
            )
        }

        val generateImplementationUseCase by lazy {
            GenerateImplementationUseCase(
                codeGenerator = environment.codeGenerator,
                generateFunctionUseCase = generateFunctionUseCase,
                generateImportsUseCase = GenerateImportsUseCase(),
                generateCompanionObjectUseCase = generateCompanionObjectUseCase,
                getPreferencesNameUseCase = GetPreferencesNameUseCase()
            )
        }

        return Processor(
            validateInterfaceUseCase = validateInterfaceUseCase,
            generateImplementationUseCase = generateImplementationUseCase
        )
    }
}
