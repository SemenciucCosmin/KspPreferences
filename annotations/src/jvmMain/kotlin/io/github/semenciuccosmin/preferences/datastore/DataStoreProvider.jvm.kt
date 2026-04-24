package io.github.semenciuccosmin.preferences.datastore

import okio.Path
import okio.Path.Companion.toPath

actual fun dataStorePreferencesPath(context: Any, name: String): Path {
    require(context is String) { "Expected a String directory path but got ${context::class}" }
    return "$context/datastore/$name.preferences_pb".toPath()
}

