package data.multiaccounts.sources

import android.content.SharedPreferences
import androidx.core.content.edit
import data.ktor.models.Token
import javax.inject.Inject

class AccountPreferencesSource @Inject constructor(private val preferences: SharedPreferences) {
    fun getAccessToken(id: Int) = preferences.getString("${id}_ACCESS_TOKEN", null)
        ?.let(Result.Companion::success) ?: Result.failure(Exception("No access token found"))

    fun isSignedIn(id: Int) = preferences.getBoolean("${id}_IS_SIGNED_IN", false)

    fun getRefreshToken(id: Int) = preferences.getString("${id}_REFRESH_TOKEN", null)

    fun deleteAccount(id: String) {
        preferences.edit {
            remove("${id}_ACCESS_TOKEN")
            remove("${id}_REFRESH_TOKEN")
            remove("${id}_IS_SIGNED_IN")
        }
    }

    fun setTokens(id: Int, token: Token) {
        preferences.edit {
            putString("${id}_ACCESS_TOKEN", token.accessToken)
            putString("${id}_REFRESH_TOKEN", token.refreshToken)
            putBoolean("${id}_IS_SIGNED_IN", true)
        }
    }
}
