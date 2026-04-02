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

/**
 * Bootstraps the Koin dependency-injection container for the KSP processor.
 *
 * Because the KSP processor runs inside the Kotlin compiler daemon (not inside an Android
 * process), there is no framework-managed DI lifecycle. [KoinInitializer] creates and
 * configures the Koin container manually at the start of each processing round, wiring
 * together all use-cases, validators, and code-generators that the processor depends on.
 *
 * All dependencies are registered as **factories** so that a fresh instance is resolved
 * every time one is requested. This keeps each processing invocation stateless and avoids
 * stale state between incremental KSP rounds.
 */
object KoinInitializer {

    /**
     * Starts a Koin container and registers all processor dependencies.
     *
     * Should be called once at the beginning of a KSP processing round, typically from
     * `SymbolProcessor.process()`. The [environmentLogger] and [codeGenerator] are
     * KSP-provided objects that cannot be constructed by Koin itself, so they are captured
     * from the call site and supplied to the relevant factories via closures.
     *
     * @param environmentLogger The [KSPLogger] provided by the KSP runtime, used to report
     *                          validation errors and warnings back to the build system.
     * @param codeGenerator     The [CodeGenerator] provided by the KSP runtime, used to
     *                          create and write the generated `*Impl` source files.
     */
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