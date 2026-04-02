package com.ksp.preferences.compiler.annotations

import com.ksp.preferences.annotations.Get
import com.ksp.preferences.annotations.GetFlow
import com.ksp.preferences.annotations.Set

/**
 * Registry of all KSP accessor annotations recognized by the processor.
 *
 * An *accessor annotation* controls the kind of DataStore operation a function represents:
 * - [Get]     — suspending point-in-time read.
 * - [GetFlow] — non-suspending reactive read that returns a `Flow`.
 * - [Set]     — suspending write.
 *
 * Each function in a [com.ksppreferences.annotations.Preferences]-annotated interface must
 * carry exactly one of these annotations together with a value-type annotation from
 * [ValueTypeAnnotations].
 */
internal object AccessorAnnotations {
    val all = listOf(
        Get::class,
        GetFlow::class,
        Set::class
    )

    val allString = all.mapNotNull { it.simpleName }.toString()
}
