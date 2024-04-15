package data.multiaccounts.sources

import data.ktor.BuildConfig
import data.ktor.models.Token
import data.ktor.utils.post
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import javax.inject.Inject

class AuthSource @Inject constructor(private val client: HttpClient) {
    suspend fun getTokens(authorizationCode: String, codeVerifier: String) = client.post<Token>(
        "https://myanimelist.net/v1/oauth2/token",
    ) {
        header("Authorization", "Basic ${BuildConfig.API_KEY}")
        setBody(FormDataContent(Parameters.build {
            append("client_id", BuildConfig.API_KEY)
            append("response_type", "code")
            append("grant_type", "authorization_code")
            append("code", authorizationCode)
            append("redirect_uri", "shov.studio.trackanimelist://callback")
            append("code_verifier", codeVerifier)
        }))
    }

    suspend fun refreshToken(refreshToken: String) = client.post<Token>(
        "https://myanimelist.net/v1/oauth2/token"
    ) {
        setBody(FormDataContent(Parameters.build {
            append("client_id", BuildConfig.API_KEY)
            append("grant_type", "refresh_token")
            append("refresh_token", refreshToken)
        }))
    }
}