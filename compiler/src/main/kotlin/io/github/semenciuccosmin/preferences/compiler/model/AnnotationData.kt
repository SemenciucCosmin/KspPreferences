package io.github.semenciuccosmin.preferences.compiler.model

import com.google.devtools.ksp.symbol.KSType

/**
 * Holds the resolved metadata extracted from a value-type annotation on a single function.
 *
 * @property keyName       The DataStore preference key string, or `null` if unresolvable.
 * @property defaultValue  The formatted default value literal (e.g. `"0f"`, `"\"\""`, `"false"`),
 *                         or `null` if unresolvable.
 * @property primitiveType The raw default value from the annotation argument for primitive types
 *                         (used to determine the Kotlin type at generation time), or `null` for
 *                         object types.
 * @property objectType    The [com.google.devtools.ksp.symbol.KSType] of the serialisable object
 *                         type when the function is annotated with
 *                         [io.github.semenciuccosmin.preferences.annotations.ObjectPreference],
 *                         or `null` for primitive types.
 */
data class AnnotationData(
    val keyName: String?,
    val defaultValue: String?,
    val primitiveType: Any?,
    val objectType: Any?
) {
    /**
     * The simple Kotlin type name of the preference value (e.g. `"String"`, `"Int"`).
     *
     * Derived from [primitiveType] for primitive preferences, or from the declaration name
     * of [objectType] for object preferences. Returns `null` when neither can be resolved.
     */
    val typeName: String?
        get() {
            val objectTypeName = (objectType as? KSType)?.declaration?.simpleName?.asString()
            return primitiveType?.let { it::class.simpleName } ?: objectTypeName
        }
}
