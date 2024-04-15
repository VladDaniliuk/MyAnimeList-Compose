package shov.studio.domain.core.data.converters

import data.common.datas.RelatedResponse
import shov.studio.domain.core.data.models.RelatedModelEntity

internal fun RelatedResponse.toRelatedEntity() = relatedAnime.map { response ->
    RelatedModelEntity(
        id = response.node.id,
        title = response.node.title,
        picture = response.node.mainPicture?.medium,
        rating = response.node.mean,
        synopsis = response.node.synopsis
    )
}
