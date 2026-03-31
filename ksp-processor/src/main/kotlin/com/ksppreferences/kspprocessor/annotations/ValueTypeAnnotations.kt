package com.ksppreferences.kspprocessor.annotations

import com.ksppreferences.annotations.BooleanPreference
import com.ksppreferences.annotations.BytePreference
import com.ksppreferences.annotations.CharPreference
import com.ksppreferences.annotations.DoublePreference
import com.ksppreferences.annotations.FloatPreference
import com.ksppreferences.annotations.IntPreference
import com.ksppreferences.annotations.LongPreference
import com.ksppreferences.annotations.ShortPreference
import com.ksppreferences.annotations.StringPreference

internal object ValueTypeAnnotations {
    val all = listOf(
        BooleanPreference::class,
        BytePreference::class,
        CharPreference::class,
        DoublePreference::class,
        FloatPreference::class,
        IntPreference::class,
        LongPreference::class,
        ShortPreference::class,
        StringPreference::class,
    )

    val allString = all.mapNotNull { it.simpleName }.toString()
}
