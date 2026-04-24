package io.github.semenciuccosmin.preferences.compiler.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.github.semenciuccosmin.preferences.annotations.Preferences
import io.github.semenciuccosmin.preferences.compiler.usecase.GenerateImplementationUseCase
import io.github.semenciuccosmin.preferences.compiler.usecase.ValidateInterfaceUseCase

/**
 * KSP [SymbolProcessor] that drives the end-to-end code generation pipeline for
 * [@Preferences][io.github.semenciuccosmin.preferences.annotations.Preferences]-annotated interfaces.
 */
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
