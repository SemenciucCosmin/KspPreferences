package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class DoublePreference(
    val key: String,
    val defaultValue: Double = 0.0
)
