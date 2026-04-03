package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import io.github.semenciuccosmin.preferences.compiler.model.AnnotationData

/**
 * Generates the source code for a `Flow`-returning getter override in a DataStore
 * preferences implementation.
 *
 * The produced function maps the DataStore data stream and emits the stored value on
 * every change, falling back to the declared default when the key is absent.
 */
internal class GenerateGetFlowFunctionUseCase {

    /**
     * Produces the Kotlin source string for a `Flow`-based getter override.
     *
     * @param functionName   The exact function name as declared in the interface.
     * @param annotationData The resolved metadata for the preference (key, default value, type).
     * @return Kotlin source code for the override, ready to be appended to the implementation
     *         class body.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(
        functionName: String,
        annotationData: AnnotationData,
    ): String {
        return when (annotationData.objectType != null) {
            true -> buildString {
                appendLine(
                    """ 
                    |    override fun $functionName(): Flow<${annotationData.typeName}?> {
                    |        return context.dataStore.data.map {
                    |            val jsonString = it[${annotationData.keyName}]
                    |            jsonString?.let { Json.decodeFromString(it) }
                    |        }
                    |    }
                    """.trimMargin()
                )
            }

            false -> buildString {
                appendLine(
                    """ 
                    |    override fun $functionName(): Flow<${annotationData.typeName}> {
                    |        return context.dataStore.data.map { it[${annotationData.keyName}] ?: ${annotationData.defaultValue} }
                    |    }
                    """.trimMargin()
                )
            }
        }
    }
}
