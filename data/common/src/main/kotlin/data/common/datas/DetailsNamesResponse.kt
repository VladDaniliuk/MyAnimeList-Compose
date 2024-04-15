package data.common.datas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailsNamesResponse(
    val title: String,
    @SerialName("alternative_titles") val alternativeTitles: AlternativeTitlesFullResponse,
)
