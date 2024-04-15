package shov.studio.feature.domain.models.data.converters

import shov.studio.feature.domain.models.data.models.AnimeStatisticsEntity
import data.user.models.AnimeStatisticsResponse

fun AnimeStatisticsResponse.toEntity() = AnimeStatisticsEntity(
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
