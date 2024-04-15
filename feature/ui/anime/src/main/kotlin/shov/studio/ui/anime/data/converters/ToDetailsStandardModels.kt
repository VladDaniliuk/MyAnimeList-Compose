package shov.studio.ui.anime.data.converters

import shov.studio.domain.core.data.models.RelatedModelEntity
import shov.studio.ui.anime.data.models.DetailsStandardModel

internal fun RelatedModelEntity.toDetailsStandardModels() = DetailsStandardModel(
        id = id,
        title = title,
        picture = picture,
        rating = rating,
        synopsis = synopsis
    )
