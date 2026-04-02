package com.ksp.preferences.compiler.processor

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.ksp.preferences.compiler.di.KoinInitializer
import com.ksp.preferences.compiler.usecase.GenerateImplementationUseCase
import com.ksp.preferences.compiler.usecase.ValidateInterfaceUseCase
import org.koin.mp.KoinPlatform.getKoin

internal class ProcessorProvider : SymbolProcessorProvider {

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
