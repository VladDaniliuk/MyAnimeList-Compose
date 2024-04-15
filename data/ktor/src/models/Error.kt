package data.ktor.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class Error(
    @SerialName("error") val error: String,
    @SerialName("message") val message: String,
)
