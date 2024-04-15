package shov.studio.ui.anime.data.converters

import kotlinx.collections.immutable.toImmutableList
import shov.studio.domain.core.data.models.DetailsNamesEntity
import shov.studio.ui.anime.data.models.AlternativeTitlesModel

internal fun DetailsNamesEntity.toAlternativeTitlesModel() = AlternativeTitlesModel(
    title = title,
    en = en,
    ja = ja,
    synonyms = synonyms.toImmutableList()
)
