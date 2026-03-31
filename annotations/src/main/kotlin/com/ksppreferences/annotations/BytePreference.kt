package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class BytePreference(
    val key: String,
    val defaultValue: Byte = 0
)
