package data.common.datas

import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse(
    val previous: String? = null,
    val next: String? = null,
)
