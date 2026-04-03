package io.github.semenciuccosmin.preferences.compiler.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import io.github.semenciuccosmin.preferences.compiler.di.KoinInitializer
import io.github.semenciuccosmin.preferences.compiler.usecase.GenerateImplementationUseCase
import io.github.semenciuccosmin.preferences.compiler.usecase.ValidateInterfaceUseCase
import org.koin.mp.KoinPlatform.getKoin

/**
 * KSP [SymbolProcessorProvider] that bootstraps the Koin DI container and creates a
 * fully-wired [Processor] instance.
 *
 * KSP calls [create] once per compilation round. Koin is initialised here with the KSP
 * [SymbolProcessorEnvironment] so that all use-cases have access to the environment's
 * [com.google.devtools.ksp.processing.KSPLogger] and
 * [com.google.devtools.ksp.processing.CodeGenerator].
 */
internal class ProcessorProvider : SymbolProcessorProvider {

    /**
     * Initialises Koin, resolves the required use-cases from the DI graph, and returns a
     * configured [Processor].
     *
     * @param environment The KSP environment providing the logger and code generator.
     * @return A [Processor] ready to process [@Preferences][io.github.semenciuccosmin.preferences.annotations.Preferences]-annotated interfaces.
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
