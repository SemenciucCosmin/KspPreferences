package io.github.semenciuccosmin.preferences.factory

/**
 * Interface for constructing a generated preferences implementation.
 *
 * Modeled after Room's `RoomDatabaseConstructor`, this interface is implemented by
 * a KSP-generated `actual object` that the user declares as an `expect object`.
 *
 * @param T The preferences interface type.
 */
interface PreferencesConstructor<T> {

    /**
     * Creates a new instance of the generated preferences implementation.
     *
     * @param context Platform-specific context required by DataStore.
     *                On Android this is the `Context`; on other platforms it can be `null`.
     * @return A fully initialized instance of [T].
     */
    fun initialize(context: Any? = null): T
}

