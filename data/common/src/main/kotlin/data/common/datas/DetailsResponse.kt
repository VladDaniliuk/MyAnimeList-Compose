package data.common.datas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import shov.studio.enums.MediaType
import shov.studio.enums.DetailsStatus
import data.common.converters.DateSerializer

@Serializable
data class DetailsResponse(
    val id: Int,
    val title: String,
    @SerialName("main_picture") val mainPicture: MainPictureMediumResponse? = null,
    @SerialName("alternative_titles") val alternativeTitles: AlternativeTitlesDetailsResponse,
    @SerialName("start_date") @Serializable(DateSerializer::class) val startDate: String? = null,
    @SerialName("end_date") @Serializable(DateSerializer::class) val endDate: String? = null,
    val synopsis: String,
    val mean: Float? = null,
    val rank: Int? = null,
    val popularity: Int? = null,
    @SerialName("num_list_users") val numListUsers: Int? = null,
    @SerialName("num_scoring_users") val numScoringUsers: Int? = null,
    @SerialName("media_type") val mediaType: MediaType,
    val status: DetailsStatus,
    val genres: List<GenreResponse>? = null,
    @SerialName("num_volumes") val numVolumes: Int? = null,
    @SerialName("num_chapters") val numChapters: Int? = null,
    @SerialName("num_episodes") val numEpisodes: Int? = null,
    @SerialName("start_season") val startSeason: SeasonResponse? = null,
    val authors: List<AuthorResponse>? = null,
    val pictures: List<MainPictureMediumResponse>,
    @SerialName("related_anime") val relatedAnime: List<DataRelatedResponse>,
    @SerialName("related_manga") val relatedManga: List<DataRelatedResponse>,
    val recommendations: List<DataRecommendationResponse>,
    val studios: List<GenreResponse>? = null,
    val statistics: StatisticsResponse? = null,
    @SerialName("my_list_status") val listStatusResponse: StandardListStatusResponse? = null
)
