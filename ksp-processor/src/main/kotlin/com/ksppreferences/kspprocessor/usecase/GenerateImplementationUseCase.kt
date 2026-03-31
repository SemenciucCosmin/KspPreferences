package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import kotlin.sequences.forEach

internal class GenerateImplementationUseCase(
    private val codeGenerator: CodeGenerator,
    private val generateImportsUseCase: GenerateImportsUseCase,
    private val generateFunctionUseCase: GenerateFunctionUseCase,
    private val generateCompanionObjectUseCase: GenerateCompanionObjectUseCase,
    private val getPreferencesNameUseCase: GetPreferencesNameUseCase,
) {

    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration) {
        val packageName = interfaceDeclaration.packageName.asString()
        val interfaceName = interfaceDeclaration.simpleName.asString()
        val implementationName = interfaceName + IMPLEMENTATION_SUFFIX
        val preferencesName = getPreferencesNameUseCase(interfaceDeclaration)

        val fileContent = buildString {
            append(generateImportsUseCase(interfaceDeclaration))
            appendLine()

            appendLine("class $implementationName(private val context: Context) : $interfaceName {")
            appendLine()
            appendLine(
                """ 
                |    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
                |        name = "$preferencesName"
                |    )
                """.trimMargin()
            )

            interfaceDeclaration.getAllFunctions().forEach { function ->
                generateFunctionUseCase(function)?.let {
                    appendLine()
                    append(it)
                }
            }

            appendLine()
            append(generateCompanionObjectUseCase(interfaceDeclaration))

            appendLine("}")
        }

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, interfaceDeclaration.containingFile ?: return),
            packageName = packageName,
            fileName = implementationName
        )

        file.bufferedWriter().use { it.write(fileContent) }
    }

    companion object {
        private const val IMPLEMENTATION_SUFFIX = "Impl"
    }
}
