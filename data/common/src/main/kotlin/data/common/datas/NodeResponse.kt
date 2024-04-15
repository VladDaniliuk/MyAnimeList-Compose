package data.common.datas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NodeRecommendationResponse(
    val id: Int,
    val title: String,
    @SerialName("main_picture") val mainPicture: MainPictureMediumResponse? = null,
)

@Serializable
data class NodeRelatedResponse(
    val id: Int,
    val title: String,
    @SerialName("main_picture") val mainPicture: MainPictureMediumResponse? = null,
    @SerialName("start_season") val startSeason: SeasonResponse? = null,
    val mean: Float? = null,
)

@Serializable
data class NodeStandardResponse(
    val id: Int,
    val title: String,
    @SerialName("main_picture") val mainPicture: MainPictureMediumResponse? = null,
    val mean: Float? = null,
    val synopsis: String,
)
