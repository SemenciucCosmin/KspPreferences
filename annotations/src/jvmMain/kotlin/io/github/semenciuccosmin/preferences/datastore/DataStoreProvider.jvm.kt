package io.github.semenciuccosmin.preferences.datastore

import okio.Path
import okio.Path.Companion.toPath

actual fun dataStorePreferencesPath(context: Any?, name: String): Path {
    val userHome = System.getProperty("user.home")
    return "$userHome/.local/share/datastore/$name.preferences_pb".toPath()
}
