package io.github.semenciuccosmin.preferences.datastore
import android.content.Context
import okio.Path
import okio.Path.Companion.toPath

actual fun dataStorePreferencesPath(context: Any?, name: String): Path {
    require(context != null) { "Expected android.content.Context but got null" }
    require(context is Context) { "Expected android.content.Context but got ${context::class}" }
    return context.filesDir.resolve("datastore/$name.preferences_pb").absolutePath.toPath()
}
