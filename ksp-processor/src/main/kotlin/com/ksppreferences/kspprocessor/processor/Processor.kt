package com.ksppreferences.kspprocessor.processor

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.ClassKind
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.ksppreferences.annotations.Preferences
import com.ksppreferences.kspprocessor.usecase.GenerateImplementationUseCase
import com.ksppreferences.kspprocessor.usecase.ValidateInterfaceUseCase

/**
 * KSP [SymbolProcessor] that drives the end-to-end code generation pipeline for
 * [@Preferences][com.ksppreferences.annotations.Preferences]-annotated interfaces.
 *
 * For every interface annotated with [@Preferences][com.ksppreferences.annotations.Preferences]
 * found in the compilation unit, the processor:
 * 1. Validates the interface structure via [ValidateInterfaceUseCase] — ensuring each
 *    declared function carries the correct annotation combination and a matching declaration.
 * 2. Generates the concrete `*Impl` DataStore implementation via [GenerateImplementationUseCase]
 *    — but only when validation succeeds, so that a malformed interface does not produce
 *    broken source files that would cause secondary compile errors.
 *
 * Non-interface symbols and symbols without the [@Preferences][com.ksppreferences.annotations.Preferences]
 * annotation are silently ignored. The processor always returns an empty deferred list
 * because all annotated symbols are fully resolved in a single round.
 */
internal class Processor(
    private val validateInterfaceUseCase: ValidateInterfaceUseCase,
    private val generateImplementationUseCase: GenerateImplementationUseCase,
) : SymbolProcessor {

    /**
     * Processes all [@Preferences][com.ksppreferences.annotations.Preferences]-annotated
     * interfaces discovered by the KSP [Resolver] in the current compilation round.
     *
     * @param resolver The KSP [Resolver] used to locate annotated symbols in the current
     *                 round's source set.
     * @return An empty list — all annotated symbols are handled in a single round with no
     *         deferred symbols.
     */
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
