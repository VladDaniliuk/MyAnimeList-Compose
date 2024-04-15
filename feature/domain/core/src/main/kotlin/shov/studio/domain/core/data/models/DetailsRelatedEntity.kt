package shov.studio.domain.core.data.models

import shov.studio.enums.RelationType
import shov.studio.enums.Season

data class DetailsRelatedEntity(
    val id: Int,
    val title: String,
    val picture: String?,
    val startYear: Int?,
    val startSeason: Season?,
    val rating: Float?,
    val relationType: RelationType,
)
