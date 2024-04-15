package shov.studio.data.anime.sources

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import shov.studio.enums.Season
import shov.studio.enums.SortType
import data.common.datas.StandardResponse
import data.ktor.utils.get
import javax.inject.Inject

class AnimeSource @Inject constructor(private val client: HttpClient) {
    suspend fun season(year: Int, season: Season, offset: Int, sort: SortType) =
        client.get<StandardResponse>(
            "https://api.myanimelist.net/v2/anime/season/$year/$season" +
                    "?limit=10&fields=mean,synopsis&offset=$offset&sort=$sort"
        )

    suspend fun suggested(offset: Int, token: String) = client.get<StandardResponse>(
        "https://api.myanimelist.net/v2/anime/suggestions?limit=10&offset=$offset" +
                "&fields=mean,synopsis"
    ) {
        header("Authorization", "Bearer $token")
    }
}
