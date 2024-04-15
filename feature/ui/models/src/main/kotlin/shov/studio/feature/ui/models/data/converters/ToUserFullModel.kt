package shov.studio.feature.ui.models.data.converters

import shov.studio.feature.domain.models.data.models.UserFullEntity
import shov.studio.feature.ui.models.data.models.UserFullModel

fun UserFullEntity.toUserFullModel() =
    UserFullModel(id, name, picture, joinedAt, location, animeStatistics.toStatisticsModel())
