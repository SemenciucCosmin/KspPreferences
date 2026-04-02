package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental

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
     * @param functionName              The exact function name as declared in the interface.
     * @param preferencesKeyName        The companion-object constant name for the preferences key.
     * @param preferencesDefaultValue   The literal default value to use when the key is absent.
     * @param preferencesDefaultValueType The simple Kotlin type name (e.g. `"Int"`, `"String"`).
     * @return Kotlin source code for the override, ready to be appended to the implementation
     *         class body.
     */
    @OptIn(KspExperimental::class)
    operator fun invoke(
        functionName: String,
        preferencesKeyName: String,
        preferencesDefaultValue: String,
        preferencesDefaultValueType: String,
    ): String {
        return buildString {
            appendLine(
                """ 
                |    override suspend fun $functionName(): $preferencesDefaultValueType {
                |        val preferences = context.dataStore.data.firstOrNull()
                |        return preferences?.get($preferencesKeyName) ?: $preferencesDefaultValue
                |    }
                """.trimMargin()
            )
        }
    }
}
