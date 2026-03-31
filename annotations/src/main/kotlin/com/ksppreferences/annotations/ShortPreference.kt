package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ShortPreference(
    val key: String,
    val defaultValue: Short = 0
)
