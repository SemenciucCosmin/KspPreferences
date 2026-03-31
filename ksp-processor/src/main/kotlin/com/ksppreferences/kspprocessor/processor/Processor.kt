package com.ksppreferences.kspprocessor.processor

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksppreferences.annotations.Preferences
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionAnnotationsUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateFunctionDeclarationUseCase

internal class Processor(
    private val codeGenerator: CodeGenerator,
    private val validateFunctionAnnotationsUseCase: ValidateFunctionAnnotationsUseCase,
    private val validateFunctionDeclarationUseCase: ValidateFunctionDeclarationUseCase,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val preferencesAnnotationName = Preferences::class.qualifiedName ?: return emptyList()
        resolver.getSymbolsWithAnnotation(preferencesAnnotationName)
            .filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == ClassKind.INTERFACE }
            .forEach(::generateImplementation)

        return emptyList()
    }

    @OptIn(KspExperimental::class)
    private fun generateImplementation(preferencesInterfaceSymbol: KSClassDeclaration) {
        preferencesInterfaceSymbol.getDeclaredFunctions().forEach { function ->
            if (!validateFunctionAnnotationsUseCase(function)) return
            if (!validateFunctionDeclarationUseCase(function)) return
        }
    }
}
