package com.ksppreferences.annotations

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.SOURCE)
annotation class ByteArrayPreference(
    val key: String,
    val defaultValue: ByteArray = []
)
