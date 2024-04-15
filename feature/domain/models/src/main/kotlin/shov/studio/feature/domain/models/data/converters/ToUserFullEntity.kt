package shov.studio.feature.domain.models.data.converters

import shov.studio.feature.domain.models.data.models.UserFullEntity
import data.user.models.UserFullResponse

fun UserFullResponse.toEntity() =
    UserFullEntity(id, name, picture, joinedAt, location, animeStatistics.toEntity())
