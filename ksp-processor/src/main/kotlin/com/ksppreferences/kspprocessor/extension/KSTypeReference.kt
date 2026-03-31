package com.ksppreferences.kspprocessor.extension

import com.google.devtools.ksp.symbol.KSTypeReference
import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

internal fun KSTypeReference?.isTypeOf(kClass: KClass<*>): Boolean {
    val resolved = this?.resolve() ?: return false
    return resolved.declaration.qualifiedName?.asString() == kClass.qualifiedName
}

internal fun KSTypeReference?.isFlowOf(kClass: KClass<*>): Boolean {
    val resolved = this?.resolve() ?: return false
    val isFlow = resolved.declaration.qualifiedName?.asString() == Flow::class.qualifiedName
    val innerType = resolved.arguments.firstOrNull()?.type?.resolve()
    val matchesInner = innerType?.declaration?.qualifiedName?.asString() == kClass.qualifiedName
    return isFlow && matchesInner
}