package com.ksppreferences.kspprocessor.di

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
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
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object KoinInitializer {

    fun initialize(
        environmentLogger: KSPLogger,
        codeGenerator: CodeGenerator
    ) {
        startKoin {
            modules(
                module {
                    factory<Logger> { Logger(environmentLogger) }

                    factoryOf(::GetValueTypeAnnotationData)

                    factoryOf(::ValidateGetFunctionDeclarationUseCase)
                    factoryOf(::ValidateGetFlowFunctionDeclarationUseCase)
                    factoryOf(::ValidateSetFunctionDeclarationUseCase)
                    factoryOf(::ValidateClearFunctionDeclarationUseCase)
                    factoryOf(::ValidateFunctionAnnotationsUseCase)
                    factoryOf(::ValidateFunctionDeclarationUseCase)
                    factoryOf(::ValidateInterfaceUseCase)

                    factoryOf(::GenerateGetFunctionUseCase)
                    factoryOf(::GenerateGetFlowFunctionUseCase)
                    factoryOf(::GenerateSetFunctionUseCase)
                    factoryOf(::GenerateClearFunctionUseCase)
                    factoryOf(::GenerateFunctionUseCase)
                    factoryOf(::GetPreferencesNameUseCase)
                    factoryOf(::GenerateCompanionObjectUseCase)
                    factoryOf(::GenerateImportsUseCase)
                    factory {
                        GenerateImplementationUseCase(
                            logger = get(),
                            codeGenerator = codeGenerator,
                            generateImportsUseCase = get(),
                            generateFunctionUseCase = get(),
                            generateCompanionObjectUseCase = get(),
                            getPreferencesNameUseCase = get()
                        )
                    }
                }
            )
        }
    }
}