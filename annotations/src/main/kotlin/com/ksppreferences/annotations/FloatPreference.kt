package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class FloatPreference(
    val key: String,
    val defaultValue: Float = 0f
)
