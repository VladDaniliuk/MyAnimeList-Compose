package data.common.datas

import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(val node: NodeAuthorResponse, val role: String)
