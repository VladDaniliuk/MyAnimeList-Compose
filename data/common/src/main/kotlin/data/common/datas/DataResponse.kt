package data.common.datas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import shov.studio.enums.RelationType

@Serializable
data class DataRecommendationResponse(val node: NodeRecommendationResponse)

@Serializable
data class DataRelatedResponse(
    val node: NodeRelatedResponse,
    @SerialName("relation_type") val relationType: RelationType
)

@Serializable
data class DataStandardResponse(val node: NodeStandardResponse)
