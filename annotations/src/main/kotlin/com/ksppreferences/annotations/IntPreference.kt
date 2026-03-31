package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class IntPreference(
    val key: String,
    val defaultValue: Int = 0
)
