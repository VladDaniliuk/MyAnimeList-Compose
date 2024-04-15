package shov.studio.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import shov.studio.core.data.DataStoreKeys
import shov.studio.core.data.Theme
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {
    fun getTheme() = dataStore.data.map { preferences ->
        Theme.valueOf(preferences[DataStoreKeys.THEME] ?: Theme.DARK.name)
    }

    fun getIsDynamicColor() = dataStore.data.map { preferences ->
        preferences[DataStoreKeys.IS_DYNAMIC_COLOR] ?: true
    }

    suspend fun setTheme(theme: Theme) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.THEME] = theme.name
        }
    }

    suspend fun setIsDynamicColor(isDynamicColor: Boolean) {
        dataStore.edit { preferences ->
            preferences[DataStoreKeys.IS_DYNAMIC_COLOR] = isDynamicColor
        }
    }
}
