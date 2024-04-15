package data.common.datas

import kotlinx.serialization.Serializable

@Serializable
data class MainPictureMediumResponse(val medium: String)

@Serializable
data class MainPictureLargeResponse(val large: String)
