package com.ksppreferences.kspprocessor.annotations

import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.Set
import com.ksppreferences.annotations.GetFlow

internal object AccessorAnnotations {
    val all = listOf(
        Get::class,
        GetFlow::class,
        Set::class
    )

    val allString = all.joinToString(", ") { it.toString() }
}

