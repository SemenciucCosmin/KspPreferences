package com.ksppreferences.kspprocessor.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksppreferences.annotations.Preferences
import com.ksppreferences.kspprocessor.usecase.GenerateImplementationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateInterfaceUseCase

internal class Processor(
    private val validateInterfaceUseCase: ValidateInterfaceUseCase,
    private val generateImplementationUseCase: GenerateImplementationUseCase,
) : SymbolProcessor {

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val preferencesAnnotationName = Preferences::class.qualifiedName ?: return emptyList()
        resolver.getSymbolsWithAnnotation(preferencesAnnotationName)
            .filterIsInstance<KSClassDeclaration>()
            .filter { it.classKind == ClassKind.INTERFACE }
            .forEach { interfaceDeclaration ->
                if (validateInterfaceUseCase(interfaceDeclaration)) {
                    generateImplementationUseCase(interfaceDeclaration)
                }
            }

        return emptyList()
    }
}
