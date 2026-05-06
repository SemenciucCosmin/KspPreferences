package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFile
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.Modifier
import io.github.semenciuccosmin.preferences.annotations.ConstructedBy

/**
 * Generates the constructor object file for the [ConstructedBy]-referenced constructor.
 *
 * Reads the [ConstructedBy] annotation from the interface declaration to determine the
 * constructor object name, then writes an object implementing
 * `PreferencesConstructor<T>` whose `initialize` method returns a new `*Impl` instance.
 *
 * If the user-declared constructor object is an `expect` declaration (KMP projects),
 * the generated object is prefixed with `actual`. For non-KMP projects the generated
 * object is a plain `object`.
 */
internal class GenerateConstructorObjectUseCase(
    private val codeGenerator: CodeGenerator,
) {

    /**
     * Generates and writes the constructor object source file.
     *
     * Does nothing if the interface is not annotated with [ConstructedBy].
     *
     * @param interfaceDeclaration The KSP declaration of the annotated interface.
     * @param packageName The package of the interface.
     * @param interfaceName The simple name of the interface.
     * @param implementationName The simple name of the generated implementation class.
     * @param interfaceFile The source file containing the interface declaration.
     */
    operator fun invoke(
        interfaceDeclaration: KSClassDeclaration,
        packageName: String,
        interfaceName: String,
        implementationName: String,
        interfaceFile: KSFile,
    ) {
        val constructedByAnnotation = interfaceDeclaration.annotations.firstOrNull { annotation ->
            annotation.shortName.asString() == ConstructedBy::class.simpleName
        } ?: return

        val annotationArguments = constructedByAnnotation.arguments
        val constructorType = annotationArguments.firstOrNull()?.value as? KSType ?: return
        val constructorDeclaration = constructorType.declaration as? KSClassDeclaration
        val constructorName = constructorType.declaration.simpleName.asString()
        val constructorPackage = constructorType.declaration.packageName.asString()

        val isExpect = constructorDeclaration?.modifiers?.contains(Modifier.EXPECT) == true
        val objectPrefix = if (isExpect) "actual object" else "object"

        val constructorContent = buildString {
            appendLine("package $constructorPackage")

            appendLine()
            appendLine("import io.github.semenciuccosmin.preferences.factory.PreferencesConstructor")
            if (constructorPackage != packageName) {
                appendLine("import $packageName.$interfaceName")
                appendLine("import $packageName.$implementationName")
            }

            appendLine()
            appendLine("$objectPrefix $constructorName : PreferencesConstructor<$interfaceName> {")
            appendLine("    override fun initialize(context: Any?): $interfaceName {")
            appendLine("        return $implementationName(context)")
            appendLine("    }")
            appendLine("}")
        }

        val constructorFile = codeGenerator.createNewFile(
            dependencies = Dependencies(false, interfaceFile),
            packageName = constructorPackage,
            fileName = constructorName,
        )

        constructorFile.bufferedWriter().use { it.write(constructorContent) }
    }
}
