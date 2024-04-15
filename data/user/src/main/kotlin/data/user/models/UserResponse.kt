package data.user.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val name: String,
    val picture: String? = null,
)

@Serializable
data class UserFullResponse(
    @SerialName("anime_statistics") val animeStatistics: AnimeStatisticsResponse,
    val id: Int,
    @SerialName("joined_at") @Serializable(data.common.converters.DateSerializer::class) val joinedAt: String,
    val location: String,
    val name: String,
    val picture: String? = null,
)
