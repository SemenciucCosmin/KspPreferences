package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental
import io.github.semenciuccosmin.preferences.compiler.model.AnnotationData

/**
 * Generates the source code for a suspending setter override in a DataStore preferences
 * implementation.
 *
 * The produced function suspends and persists the supplied value to the DataStore under
 * the declared preference key.
 */
internal class GenerateSetFunctionUseCase {

    /**
     * Produces the Kotlin source string for a suspending setter override.
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
        return when(annotationData.objectType != null) {
            true -> buildString {
                appendLine(
                    """ 
                |    override suspend fun $functionName(value: ${annotationData.typeName}) {
                |        val jsonString = Json.encodeToString(value) 
                |        context.dataStore.edit { it[${annotationData.keyName}] = jsonString }
                |    }
                """.trimMargin()
                )
            }

            false -> buildString {
                appendLine(
                    """ 
                |    override suspend fun $functionName(value: ${annotationData.typeName}) {
                |        context.dataStore.edit { it[${annotationData.keyName}] = value }
                |    }
                """.trimMargin()
                )
            }
        }
    }
}
