package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import io.github.semenciuccosmin.preferences.compiler.model.AnnotationData

/**
 * Generates the source code for a suspending getter override in a DataStore preferences
 * implementation.
 *
 * The produced function reads a single snapshot from the DataStore and returns the stored
 * value, or falls back to the declared default when the key is absent.
 */
internal class GenerateGetFunctionUseCase {

    /**
     * Produces the Kotlin source string for a suspending getter override.
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
                    |    override suspend fun $functionName(): ${annotationData.typeName}? {
                    |        val preferences = context.dataStore.data.firstOrNull()
                    |        val jsonString = preferences?.get(${annotationData.keyName})
                    |        return jsonString?.let { Json.decodeFromString(it) }
                    |    }
                    """.trimMargin()
                )
            }

            false -> buildString {
                appendLine(
                    """ 
                    |    override suspend fun $functionName(): ${annotationData.typeName} {
                    |        val preferences = context.dataStore.data.firstOrNull()
                    |        return preferences?.get(${annotationData.keyName}) ?: ${annotationData.defaultValue}
                    |    }
                    """.trimMargin()
                )
            }
        }
    }
}
