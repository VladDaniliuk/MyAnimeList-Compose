package data.common.datas

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class RelatedResponse(
    @JsonNames("related_anime", "related_manga") val relatedAnime: List<DataStandardResponse>
)
