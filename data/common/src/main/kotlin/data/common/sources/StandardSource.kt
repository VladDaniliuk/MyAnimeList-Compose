package data.common.sources

import io.ktor.client.HttpClient
import io.ktor.client.request.header
import shov.studio.enums.RankingType
import shov.studio.enums.Sort
import shov.studio.enums.Status
import data.common.datas.DetailsNamesResponse
import data.common.datas.DetailsResponse
import data.common.datas.ImagesResponse
import data.common.datas.RelatedResponse
import data.common.datas.StandardResponse
import data.ktor.utils.get
import javax.inject.Inject

class StandardSource @Inject constructor(private val client: HttpClient) {
    suspend fun get(type: String, rankingType: RankingType, offset: Int) =
        client.get<StandardResponse>(
            "https://api.myanimelist.net/v2/$type/ranking?limit=10&fields=mean,synopsis" +
                    "&ranking_type=$rankingType&offset=$offset"
        )

    suspend fun search(type: String, name: String, offset: Int) = client.get<StandardResponse>(
        "https://api.myanimelist.net/v2/$type?limit=10&fields=mean,synopsis&q=$name&" +
                "offset=$offset"
    )

    suspend fun detailsNames(type: String, id: Int) = client.get<DetailsNamesResponse>(
        "https://api.myanimelist.net/v2/$type/$id?fields=title,alternative_titles"
    )

    suspend fun related(id: Int, type: String) = client.get<RelatedResponse>(
        "https://api.myanimelist.net/v2/$type/$id?fields=related_$type{mean,synopsis}"
    )

    suspend fun images(type: String, id: Int) = client.get<ImagesResponse>(
        "https://api.myanimelist.net/v2/$type/$id?fields=main_picture,pictures"
    )

    suspend fun details(type: String, id: Int, token: String?) = client.get<DetailsResponse>(
        "https://api.myanimelist.net/v2/$type/$id?fields=id,title,main_picture," +
                "alternative_titles,start_date,end_date,synopsis,mean,rank,popularity,studios," +
                "num_list_users,num_scoring_users,media_type,status,num_episodes,start_season," +
                "genres,num_volumes,num_chapters,authors{first_name,last_name},pictures," +
                "background,related_anime{start_season,mean},related_manga{start_season,mean}," +
                "recommendations,statistics,my_list_status{tags,comments}"
    ) {
        token?.let { header("Authorization", "Bearer $token") }
    }

    suspend fun userList(type: String, offset: Int, status: Status, sort: Sort, token: String) =
        client.get<StandardResponse>(
            "https://api.myanimelist.net/v2/users/@me/${type}list?limit=10&fields=mean," +
                    "synopsis&offset=$offset&sort=$sort&status=$status"
        ) {
            header("Authorization", "Bearer $token")
        }
}
