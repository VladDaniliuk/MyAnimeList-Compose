package data.multiaccounts.repositories

import android.net.Uri
import data.ktor.models.Token
import data.multiaccounts.BuildConfig
import data.multiaccounts.sources.AccountPreferencesSource
import data.multiaccounts.sources.AuthSource
import java.security.SecureRandom
import java.util.Base64
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val accountPreferencesSource: AccountPreferencesSource,
    private val authSource: AuthSource,
) {
    fun isSignedIn(id: Int) = accountPreferencesSource.isSignedIn(id)

    suspend fun changeTokens(code: String) = authSource.getTokens(code, getVerifier())

    fun setTokens(id: Int, token: Token) {
        accountPreferencesSource.setTokens(id, token)
    }

    suspend fun refreshToken(id: Int): Result<Unit> {
        val refreshToken = accountPreferencesSource.getRefreshToken(id)
            ?: return Result.failure(NullPointerException("No refresh token"))

        return authSource.refreshToken(refreshToken).map { token ->
            accountPreferencesSource.setTokens(id, token)
        }
    }

    fun getAuthLink(): Uri = Uri.parse(
        "https://myanimelist.net/v1/oauth2/authorize?client_id=${BuildConfig.API_KEY}&" +
                "redirect_uri=shov.studio.trackanimelist://callback&response_type=code&" +
                "scope=write:users&code_challenge=${getChallenge()}&code_challenge_method=plain"
    )

    private var code = ""

    private fun getVerifier() = code.also { code = "" }

    private fun getChallenge(): String {
        val challenge = ByteArray(32)
        SecureRandom().nextBytes(challenge)

        return Base64.getUrlEncoder().withoutPadding().encodeToString(challenge).also { code = it }
    }
}
