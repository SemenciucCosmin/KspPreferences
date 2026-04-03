package io.github.semenciuccosmin.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental

/**
 * Generates the source code for a `suspend fun clear()` override in a DataStore
 * preferences implementation.
 *
 * The produced function delegates to `dataStore.edit { it.clear() }`, removing all
 * key-value pairs stored in the DataStore.
 */
internal class GenerateClearFunctionUseCase {

    /**
     * Produces the Kotlin source string for the `clear` override.
     *
     * @param functionName The exact function name as declared in the annotated interface.
     * @return Kotlin source code for the `clear` override, ready to be appended to the
     *         generated implementation class body.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(functionName: String): String {
        return buildString {
            appendLine(
                """ 
                |    override suspend fun $functionName() {
                |        context.dataStore.edit { it.clear() }
                |    }
                """.trimMargin()
            )
        }
    }
}
