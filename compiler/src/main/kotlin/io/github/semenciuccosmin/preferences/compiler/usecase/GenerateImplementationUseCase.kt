package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getDeclaredFunctions
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import io.github.semenciuccosmin.preferences.compiler.logger.Logger
import kotlin.sequences.forEach

/**
 * Orchestrates generation of the complete `*Impl` source file for a
 * [io.github.semenciuccosmin.preferences.annotations.Preferences]-annotated interface.
 *
 * The generated file contains:
 * 1. Package declaration and required imports (via [GenerateImportsUseCase]).
 * 2. A top-level `private val Context.dataStore` extension property backed by
 *    [androidx.datastore.preferences.preferencesDataStore]. Placing this at file scope
 *    ensures a single DataStore instance is shared across all usages, avoiding the
 *    "multiple DataStores active for the same file" error.
 * 3. The implementation class with overrides for every declared function
 *    (via [GenerateFunctionUseCase]).
 * 4. A `companion object` containing the DataStore name constant and all typed preference
 *    keys (via [GenerateCompanionObjectUseCase]).
 */
internal class GenerateImplementationUseCase(
    private val logger: Logger,
    private val codeGenerator: CodeGenerator,
    private val generateImportsUseCase: GenerateImportsUseCase,
    private val generateFunctionUseCase: GenerateFunctionUseCase,
    private val generateCompanionObjectUseCase: GenerateCompanionObjectUseCase,
    private val generateConstructorObjectUseCase: GenerateConstructorObjectUseCase,
) {

    /**
     * Generates and writes the implementation source file for [interfaceDeclaration].
     *
     * @param interfaceDeclaration The KSP class declaration of the annotated interface.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(interfaceDeclaration: KSClassDeclaration) {
        val packageName = interfaceDeclaration.packageName.asString()
        val interfaceName = interfaceDeclaration.simpleName.asString()
        val implementationName = interfaceName + IMPLEMENTATION_SUFFIX

        val fileContent = buildString {
            append(generateImportsUseCase(interfaceDeclaration))
            appendLine()

            appendLine("class $implementationName(context: Any?) : $interfaceName {")
            appendLine()
            appendLine(
                """ 
                |    private val dataStore: DataStore<Preferences> = createDataStore(
                |        context = context,
                |        name = PREFERENCES_NAME,
                |    )
                """.trimMargin()
            )

            interfaceDeclaration.getDeclaredFunctions().forEach { function ->
                generateFunctionUseCase(interfaceName, function)?.let {
                    appendLine()
                    append(it)
                }
            }

            appendLine()
            append(generateCompanionObjectUseCase(interfaceDeclaration))

            appendLine("}")
        }

        val interfaceFile = interfaceDeclaration.containingFile
        if (interfaceFile == null) {
            logger.logMisingInterfaceFileError(interfaceName)
            return
        }

        val file = codeGenerator.createNewFile(
            dependencies = Dependencies(false, interfaceFile),
            packageName = packageName,
            fileName = implementationName
        )

        file.bufferedWriter().use { it.write(fileContent) }

        generateConstructorObjectUseCase(
            interfaceDeclaration,
            packageName,
            interfaceName,
            implementationName,
            interfaceFile
        )
    }

    companion object {
        private const val IMPLEMENTATION_SUFFIX = "Impl"
    }
}
