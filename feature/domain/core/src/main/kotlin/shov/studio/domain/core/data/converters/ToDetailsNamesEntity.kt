package shov.studio.domain.core.data.converters

import data.common.datas.DetailsNamesResponse
import shov.studio.domain.core.data.models.DetailsNamesEntity

internal fun DetailsNamesResponse.toDetailsNamesEntity() = DetailsNamesEntity(
    title = title,
    en = alternativeTitles.en,
    ja = alternativeTitles.ja,
    synonyms = alternativeTitles.synonyms
)
