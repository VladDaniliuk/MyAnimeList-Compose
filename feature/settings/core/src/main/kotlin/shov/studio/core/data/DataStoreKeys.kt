package shov.studio.core.data

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val THEME = stringPreferencesKey("THEME")
    val IS_DYNAMIC_COLOR = booleanPreferencesKey("IS_DYNAMIC_COLOR")
}
