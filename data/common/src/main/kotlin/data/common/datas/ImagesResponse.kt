package data.common.datas

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImagesResponse(
    @SerialName("main_picture") val mainPictureResponse: MainPictureLargeResponse,
    val pictures: List<MainPictureLargeResponse>,
)
