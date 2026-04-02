package com.ksppreferences.kspprocessor.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.ksppreferences.kspprocessor.di.KoinInitializer
import com.ksppreferences.kspprocessor.usecase.GenerateImplementationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateInterfaceUseCase
import org.koin.mp.KoinPlatform.getKoin

/**
 * KSP [SymbolProcessorProvider] that bootstraps the dependency-injection container and
 * constructs a fully wired [Processor] instance for each compilation round.
 *
 * KSP calls [create] once per compilation, supplying a fresh [SymbolProcessorEnvironment]
 * that carries the round-specific [com.google.devtools.ksp.processing.KSPLogger] and
 * [com.google.devtools.ksp.processing.CodeGenerator]. These objects cannot be created by
 * Koin, so [create] passes them to [KoinInitializer.initialize] before resolving the
 * remaining dependencies from the container.
 */
internal class ProcessorProvider : SymbolProcessorProvider {

    /**
     * Initialises the Koin container and creates a [Processor] with all its dependencies.
     *
     * Steps performed:
     * 1. Calls [KoinInitializer.initialize] to start Koin and register all use-case
     *    factories, forwarding the KSP-provided [SymbolProcessorEnvironment.logger] and
     *    [SymbolProcessorEnvironment.codeGenerator].
     * 2. Resolves [ValidateInterfaceUseCase] and [GenerateImplementationUseCase] from the
     *    newly started Koin container.
     * 3. Returns a [Processor] wired with those two use-cases.
     *
     * @param environment The KSP environment for the current compilation, providing the
     *                    logger and code generator.
     * @return A fully configured [Processor] ready to handle annotated symbols.
     */
    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        KoinInitializer.initialize(
            environmentLogger = environment.logger,
            codeGenerator = environment.codeGenerator
        )

        val koin = getKoin()
        val validateInterfaceUseCase = koin.get<ValidateInterfaceUseCase>()
        val generateImplementationUseCase = koin.get<GenerateImplementationUseCase>()

        return Processor(
            validateInterfaceUseCase = validateInterfaceUseCase,
            generateImplementationUseCase = generateImplementationUseCase
        )
    }
}
