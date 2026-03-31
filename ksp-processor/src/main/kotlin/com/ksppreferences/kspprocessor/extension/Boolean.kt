package com.ksppreferences.kspprocessor.extension

internal fun Boolean.ifNot(block: () -> Unit): Boolean {
    if (!this) block()
    return this
}
