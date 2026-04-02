package io.github.semenciuccosmin.preferences.compiler.annotations

import io.github.semenciuccosmin.preferences.annotations.Clear

/**
 * Registry of all KSP functional annotations recognised by the processor.
 *
 * A *functional annotation* marks a function as a special DataStore operation that does not
 * read or write a single keyed preference:
 * - [Clear] — removes all entries from the DataStore.
 *
 * Functions annotated with a functional annotation must **not** carry any accessor annotation
 * from [AccessorAnnotations] or a value-type annotation from [ValueTypeAnnotations].
 */
internal object FunctionalAnnotations {
    val all = listOf(
        Clear::class,
    )

    val allString = all.mapNotNull { it.simpleName }.toString()
}
