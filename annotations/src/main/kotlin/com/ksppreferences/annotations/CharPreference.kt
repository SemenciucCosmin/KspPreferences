package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class CharPreference(
    val key: String,
    val defaultValue: Char = '\u0000'
)
