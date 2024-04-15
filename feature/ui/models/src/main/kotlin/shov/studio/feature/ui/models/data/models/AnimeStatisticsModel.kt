package shov.studio.feature.ui.models.data.models

data class AnimeStatisticsModel(
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
