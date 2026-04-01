package com.ksppreferences.kspprocessor.annotations

import com.ksppreferences.annotations.Clear

internal object FunctionalAnnotations {
    val all = listOf(
        Clear::class,
    )

    val allString = all.mapNotNull { it.simpleName }.toString()
}
