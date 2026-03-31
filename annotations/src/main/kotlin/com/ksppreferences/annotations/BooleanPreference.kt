package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class BooleanPreference(
    val key: String,
    val defaultValue: Boolean = false
)
