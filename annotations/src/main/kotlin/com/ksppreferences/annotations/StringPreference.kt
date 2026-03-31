package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class StringPreference(
    val key: String,
    val defaultValue: String = ""
)
