package data.common.datas

import androidx.annotation.IntRange
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import shov.studio.enums.Status

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class StandardListStatusResponse(
    val status: Status? = null,
    @IntRange(from = 0, to = 10) val score: Int,
    @SerialName("num_chapters_read") val numChaptersRead: Int? = null,
    @SerialName("num_episodes_watched") val numEpisodesWatched: Int? = null,
    @JsonNames("is_rereading", "is_rewatching") val isRereading: Boolean,
    @SerialName("num_volumes_read") val numVolumesRead: Int? = null,
    val tags: List<String>,
    val comments: String,
)
