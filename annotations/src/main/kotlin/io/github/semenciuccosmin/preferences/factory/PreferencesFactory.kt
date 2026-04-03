package io.github.semenciuccosmin.preferences.factory

object PreferencesFactory {
    inline fun <reified T : Any> create(context: Any): T {
        val implClass = Class.forName("${T::class.qualifiedName}Impl")
        val contextClass = Class.forName("android.content.Context")
        @Suppress("UNCHECKED_CAST")
        return implClass.getDeclaredConstructor(contextClass).newInstance(context) as T
    }
}
