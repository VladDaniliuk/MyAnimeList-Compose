package shov.studio.domain.core.data.converters

import data.common.datas.DataRelatedResponse
import shov.studio.domain.core.data.models.DetailsRelatedEntity

fun DataRelatedResponse.toDetailsRelatedEntity() = DetailsRelatedEntity(
    id = node.id,
    title = node.title,
    picture = node.mainPicture?.medium,
    startYear = node.startSeason?.year,
    startSeason = node.startSeason?.season,
    rating = node.mean,
    relationType = relationType
)
