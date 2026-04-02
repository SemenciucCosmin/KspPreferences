package com.ksp.preferences.compiler.usecase

import com.google.devtools.ksp.KspExperimental

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
     * @param functionName              The exact function name as declared in the interface.
     * @param preferencesKeyName        The companion-object constant name for the preferences key.
     * @param preferencesDefaultValueType The simple Kotlin type name of the parameter
     *                                    (e.g. `"Int"`, `"String"`).
     * @return Kotlin source code for the override, ready to be appended to the implementation
     *         class body.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(
        functionName: String,
        preferencesKeyName: String,
        preferencesDefaultValueType: String,
    ): String {
        return buildString {
            appendLine(
                """ 
                |    override suspend fun $functionName(value: $preferencesDefaultValueType) {
                |        context.dataStore.edit { it[$preferencesKeyName] = value }
                |    }
                """.trimMargin()
            )
        }
    }
}
