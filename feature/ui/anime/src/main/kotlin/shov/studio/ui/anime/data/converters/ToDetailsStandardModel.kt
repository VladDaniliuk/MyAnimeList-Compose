package shov.studio.ui.anime.data.converters

import shov.studio.domain.core.data.models.NodeStandardEntity
import shov.studio.ui.anime.data.models.DetailsStandardModel

internal fun NodeStandardEntity.toDetailsStandardModel() = DetailsStandardModel(
    id = id,
    title = title,
    picture = picture,
    rating = mean,
    synopsis = synopsis
)
