package shov.studio.feature.domain.models.data.models

data class UserFullEntity(
    val id: Int,
    val name: String,
    val picture: String?,
    val joinedAt: String,
    val location: String,
    val animeStatistics: AnimeStatisticsEntity
)
