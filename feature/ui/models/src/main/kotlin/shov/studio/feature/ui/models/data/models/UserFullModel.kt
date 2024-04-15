package shov.studio.feature.ui.models.data.models

data class UserFullModel(
    val id: Int,
    val name: String,
    val picture: String?,
    val joinedAt: String,
    val location: String,
    val animeStatistics: AnimeStatisticsModel,
)
