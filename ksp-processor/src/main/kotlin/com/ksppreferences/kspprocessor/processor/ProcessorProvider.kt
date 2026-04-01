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
import com.ksppreferences.kspprocessor.usecase.ValidateClearFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionAnnotationsUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateGetFlowFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateGetFunctionDeclarationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateInterfaceUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateSetFunctionDeclarationUseCase

internal class ProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        val logger = Logger(environment.logger)
        val getValueTypeAnnotationData by lazy {
            GetValueTypeAnnotationData()
        }

        val validateGetFunctionDeclarationUseCase by lazy {
            ValidateGetFunctionDeclarationUseCase(
                logger = logger,
                getValueTypeAnnotationData = getValueTypeAnnotationData
            )
        }

        val validateGetFlowFunctionDeclarationUseCase by lazy {
            ValidateGetFlowFunctionDeclarationUseCase(
                logger = logger,
                getValueTypeAnnotationData = getValueTypeAnnotationData
            )
        }

        val validateSetFunctionDeclarationUseCase by lazy {
            ValidateSetFunctionDeclarationUseCase(
                logger = logger,
                getValueTypeAnnotationData = getValueTypeAnnotationData
            )
        }

        val validateClearFunctionDeclarationUseCase by lazy {
            ValidateClearFunctionDeclarationUseCase(
                logger = logger
            )
        }

        val validateFunctionAnnotationsUseCase by lazy {
            ValidateFunctionAnnotationsUseCase(
                logger = logger
            )
        }

        val validateFunctionDeclarationUseCase by lazy {
            ValidateFunctionDeclarationUseCase(
                logger = logger,
                validateGetFunctionDeclarationUseCase = validateGetFunctionDeclarationUseCase,
                validateGetFlowFunctionDeclarationUseCase = validateGetFlowFunctionDeclarationUseCase,
                validateSetFunctionDeclarationUseCase = validateSetFunctionDeclarationUseCase,
                validateClearFunctionDeclarationUseCase = validateClearFunctionDeclarationUseCase
            )
        }

        val validateInterfaceUseCase by lazy {
            ValidateInterfaceUseCase(
                logger = logger,
                validateFunctionAnnotationsUseCase = validateFunctionAnnotationsUseCase,
                validateFunctionDeclarationUseCase = validateFunctionDeclarationUseCase
            )
        }

        val generateGetFunctionUseCase by lazy {
            GenerateGetFunctionUseCase()
        }

        val generateGetFlowFunctionUseCase by lazy {
            GenerateGetFlowFunctionUseCase()
        }

        val generateSetFunctionUseCase by lazy {
            GenerateSetFunctionUseCase()
        }

        val generateClearFunctionUseCase by lazy {
            GenerateClearFunctionUseCase()
        }

        val generateFunctionUseCase by lazy {
            GenerateFunctionUseCase(
                logger = logger,
                generateGetFunctionUseCase = generateGetFunctionUseCase,
                generateGetFlowFunctionUseCase = generateGetFlowFunctionUseCase,
                generateSetFunctionUseCase = generateSetFunctionUseCase,
                generateClearFunctionUseCase = generateClearFunctionUseCase,
                getValueTypeAnnotationData = getValueTypeAnnotationData
            )
        }

        val getPreferencesNameUseCase by lazy {
            GetPreferencesNameUseCase(
                logger = logger
            )
        }

        val generateCompanionObjectUseCase by lazy {
            GenerateCompanionObjectUseCase(
                logger = logger,
                getValueTypeAnnotationData = getValueTypeAnnotationData,
                getPreferencesNameUseCase = getPreferencesNameUseCase
            )
        }

        val generateImportsUseCase by lazy {
            GenerateImportsUseCase()
        }

        val generateImplementationUseCase by lazy {
            GenerateImplementationUseCase(
                logger = logger,
                codeGenerator = environment.codeGenerator,
                generateFunctionUseCase = generateFunctionUseCase,
                generateImportsUseCase = generateImportsUseCase,
                generateCompanionObjectUseCase = generateCompanionObjectUseCase,
                getPreferencesNameUseCase = getPreferencesNameUseCase
            )
        }

        return Processor(
            validateInterfaceUseCase = validateInterfaceUseCase,
            generateImplementationUseCase = generateImplementationUseCase
        )
    }
}
