package data.common.datas

import kotlinx.serialization.Serializable

@Serializable
data class StandardResponse(val data: List<DataStandardResponse>, val paging: PagingResponse)
