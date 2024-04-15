package data.common.datas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NodeAuthorResponse(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String
)
