package shov.studio.feature.domain.models.data.models

data class AnimeStatisticsEntity(
    val meanScore: Float,
    val numDays: Float,
    val numDaysCompleted: Float,
    val numDaysDropped: Float,
    val numDaysOnHold: Float,
    val numDaysWatched: Float,
    val numDaysWatching: Float,
    val numEpisodes: Int,
    val numItems: Int,
    val numItemsCompleted: Int,
    val numItemsDropped: Int,
    val numItemsOnHold: Int,
    val numItemsPlanToWatch: Int,
    val numItemsWatching: Int,
    val numTimesRewatched: Int
)
