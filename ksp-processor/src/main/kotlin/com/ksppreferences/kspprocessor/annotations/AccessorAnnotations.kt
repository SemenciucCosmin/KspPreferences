package com.ksppreferences.kspprocessor.annotations

import com.ksppreferences.annotations.Get
import com.ksppreferences.annotations.Set
import com.ksppreferences.annotations.GetFlow

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
