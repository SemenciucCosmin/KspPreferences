package com.ksppreferences.kspprocessor.usecase

import com.google.devtools.ksp.KspExperimental

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
                |    override fun $functionName(): Flow<$preferencesDefaultValueType> {
                |        return context.dataStore.data.map { it[$preferencesKeyName] ?: $preferencesDefaultValue }
                |    }
                """.trimMargin()
            )
        }
    }
}
