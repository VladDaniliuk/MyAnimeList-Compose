package shov.studio.feature.ui.models.data.converters

import shov.studio.feature.domain.models.data.models.AnimeStatisticsEntity
import shov.studio.feature.ui.models.data.models.AnimeStatisticsModel

fun AnimeStatisticsEntity.toStatisticsModel() = AnimeStatisticsModel(
    meanScore,
    numDays,
    numDaysCompleted,
    numDaysDropped,
    numDaysOnHold,
    numDaysWatched,
    numDaysWatching,
    numEpisodes,
    numItems,
    numItemsCompleted,
    numItemsDropped,
    numItemsOnHold,
    numItemsPlanToWatch,
    numItemsWatching,
    numTimesRewatched
)
