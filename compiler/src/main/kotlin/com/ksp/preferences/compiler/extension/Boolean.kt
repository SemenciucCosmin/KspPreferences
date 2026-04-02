package com.ksp.preferences.compiler.extension

/**
 * Executes [block] when this [Boolean] is `false`, then returns the original value unchanged.
 *
 * Useful for logging a side-effect on failure while still propagating the boolean result
 * to the caller in a single expression, e.g.:
 * ```kotlin
 * return isValid.ifNot {
 *     logger.logError(...)
 * }
 * ```
 *
 * @param block Lambda executed only when the receiver is `false`.
 * @return The original boolean value (always returned, whether `true` or `false`).
 */
internal fun Boolean.ifNot(block: () -> Unit): Boolean {
    if (!this) block()
    return this
}
