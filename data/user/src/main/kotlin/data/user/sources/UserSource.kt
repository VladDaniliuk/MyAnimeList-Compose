package data.user.sources

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import data.ktor.utils.get
import data.user.models.UserFullResponse
import data.user.models.UserResponse
import javax.inject.Inject

class UserSource @Inject constructor(private val client: HttpClient) {
    suspend fun get(token: String) = client
        .get<UserResponse>("https://api.myanimelist.net/v2/users/@me") {
            header("Authorization", "Bearer $token")
        }

    suspend fun getFull(token: String) = client
        .get<UserFullResponse>("https://api.myanimelist.net/v2/users/@me?fields=anime_statistics") {
            header("Authorization", "Bearer $token")
        }
}
