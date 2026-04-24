package io.github.semenciuccosmin.preferences.factory

actual object PreferencesFactory {
    @PublishedApi
    internal val registry: MutableMap<String, (Any?) -> Any> = mutableMapOf()

    /**
     * Register a factory function for a given preferences interface.
     * Generated code should call this during initialization.
     */
    fun <T : Any> register(qualifiedName: String, factory: (Any?) -> T) {
        registry[qualifiedName] = factory
    }

    actual inline fun <reified T : Any> create(context: Any?): T {
        val qualifiedName = T::class.qualifiedName
            ?: error("Cannot get qualified name for ${T::class}")
        val factory = registry[qualifiedName]
            ?: error("No factory registered for $qualifiedName. Make sure the generated code is initialized.")
        @Suppress("UNCHECKED_CAST")
        return factory(context) as T
    }
}

