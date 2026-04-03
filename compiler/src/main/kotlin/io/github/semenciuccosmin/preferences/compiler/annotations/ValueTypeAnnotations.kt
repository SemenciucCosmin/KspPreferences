package io.github.semenciuccosmin.preferences.compiler.annotations

import io.github.semenciuccosmin.preferences.annotations.BooleanPreference
import io.github.semenciuccosmin.preferences.annotations.DoublePreference
import io.github.semenciuccosmin.preferences.annotations.FloatPreference
import io.github.semenciuccosmin.preferences.annotations.IntPreference
import io.github.semenciuccosmin.preferences.annotations.LongPreference
import io.github.semenciuccosmin.preferences.annotations.ObjectPreference
import io.github.semenciuccosmin.preferences.annotations.StringPreference

/**
 * Registry of all value-type annotations recognized by the processor.
 *
 * A *value-type annotation* declares the DataStore key name, the Kotlin type, and the default
 * value for a single preference. Every function carrying an accessor annotation from
 * [AccessorAnnotations] must also carry exactly one of these annotations.
 *
 * Supported types:
 * - [BooleanPreference]
 * - [DoublePreference]
 * - [FloatPreference]
 * - [IntPreference]
 * - [LongPreference]
 * - [ObjectPreference]
 * - [StringPreference]
 */
internal object ValueTypeAnnotations {
    val all = listOf(
        BooleanPreference::class,
        DoublePreference::class,
        FloatPreference::class,
        IntPreference::class,
        LongPreference::class,
        ObjectPreference::class,
        StringPreference::class,
    )

    val allString = all.mapNotNull { it.simpleName }.toString()
}
