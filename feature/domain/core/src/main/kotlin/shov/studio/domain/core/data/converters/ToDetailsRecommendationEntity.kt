package shov.studio.domain.core.data.converters

import data.common.datas.DataRecommendationResponse
import shov.studio.domain.core.data.models.DetailsRecommendationEntity

fun DataRecommendationResponse.toDetailsRecommendationEntity() =
    DetailsRecommendationEntity(
        id = node.id,
        title = node.title,
        picture = node.mainPicture?.medium
    )
