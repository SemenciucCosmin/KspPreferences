package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class LongPreference(
    val key: String,
    val defaultValue: Long = 0L
)
