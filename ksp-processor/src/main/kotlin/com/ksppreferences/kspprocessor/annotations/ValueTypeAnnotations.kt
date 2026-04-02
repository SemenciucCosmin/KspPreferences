package com.ksppreferences.kspprocessor.annotations

import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.ByteArrayPreference
import com.ksppreferences.annotations.DoublePreference
import com.ksppreferences.annotations.FloatPreference
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.LongPreference
import com.ksppreferences.annotations.StringPreference

/**
 * Registry of all value-type annotations recognized by the processor.
 *
 * A *value-type annotation* declares the DataStore key name, the Kotlin type, and the default
 * value for a single preference. Every function carrying an accessor annotation from
 * [AccessorAnnotations] must also carry exactly one of these annotations.
 *
 * Supported types:
 * - [BooleanPreference]
 * - [ByteArrayPreference]
 * - [DoublePreference]
 * - [FloatPreference]
 * - [IntPreference]
 * - [LongPreference]
 * - [StringPreference]
 */
internal object ValueTypeAnnotations {
    val all = listOf(
        BooleanPreference::class,
        ByteArrayPreference::class,
        DoublePreference::class,
        FloatPreference::class,
        IntPreference::class,
        LongPreference::class,
        StringPreference::class,
    )

    val allString = all.mapNotNull { it.simpleName }.toString()
}
