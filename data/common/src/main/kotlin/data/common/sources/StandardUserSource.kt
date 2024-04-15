package data.common.sources

import data.common.datas.StandardListStatusResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.FormDataContent
import io.ktor.client.request.header
import io.ktor.client.request.setBody
import io.ktor.http.Parameters
import shov.studio.enums.Status
import data.ktor.utils.delete
import data.ktor.utils.patch
import javax.inject.Inject

class StandardUserSource @Inject constructor(private val client: HttpClient) {
    suspend fun updateStatus(type: String, id: Int, status: Enum<out Status>, token: String) =
        if (status == Status.delete) deleteListItem(type, id).fold(
            onSuccess = {
                Result.success(
                    StandardListStatusResponse(
                        score = 0, isRereading = false, tags = emptyList(), comments = ""
                    )
                )
            },
            onFailure = { throwable -> Result.failure(throwable) })
        else updateListStatus(
            type, id, "status",
            if ((status == Status.not_reading) or (status == Status.not_watching)) "" else status.name,
            token
        )

    suspend fun updateScore(type: String, id: Int, score: Int, token: String) =
        updateListStatus(type, id, "score", score.toString(), token)

    suspend fun updateTags(type: String, id: Int, tags: List<String>, token: String) =
        updateListStatus(type, id, "tags", tags.joinToString(separator = ","), token)

    suspend fun updateComments(type: String, id: Int, comments: String, token: String) =
        updateListStatus(type, id, "comments", comments, token)

    suspend fun updateIsRewatching(type: String, id: Int, isRewatching: Boolean, token: String) =
        updateListStatus(
            type, id, when (type) {
                "anime" -> "is_rewatching"
                "manga" -> "is_rereading"
                else -> throw IllegalArgumentException("$type is not accessible parameter (manga, anime)")
            }, isRewatching.toString(), token
        )

    suspend fun updateNumChaptersRead(id: Int, numChaptersRead: Int, token: String) =
        updateListStatus(
            "manga", id, "num_chapters_read", numChaptersRead.toString(), token
        )

    suspend fun updateNumVolumesRead(id: Int, numVolumesRead: Int, token: String) =
        updateListStatus(
            "manga", id, "num_volumes_read", numVolumesRead.toString(), token
        )

    suspend fun updateNumEpisodesWatched(id: Int, numWatchedEpisodes: Int, token: String) =
        updateListStatus(
            "anime", id, "num_watched_episodes", numWatchedEpisodes.toString(), token
        )

    suspend fun updateDetails(
        type: String,
        id: Int,
        status: Enum<out Status>,
        score: Int,
        tags: List<String>,
        comment: String,
        isRewatching: Boolean,
        numChaptersRead: Int?,
        numVolumesRead: Int?,
        numWatchedEpisodes: Int?,
        token: String,
    ) = client.patch<StandardListStatusResponse>(
        "https://api.myanimelist.net/v2/$type/$id/my_list_status"
    ) {
        setBody(FormDataContent(Parameters.build {
            append(
                "status",
                if ((status == Status.not_reading) or (status == Status.not_watching)) "" else status.name,
            )
            append("score", score.toString())
            append("tags", tags.joinToString(separator = ","))
            append("comments", comment)
            append(
                when (type) {
                    "anime" -> "is_rewatching"
                    "manga" -> "is_rereading"
                    else -> throw IllegalArgumentException("$type is not accessible parameter (manga, anime)")
                }, isRewatching.toString()
            )
            numChaptersRead?.let { numChaptersRead ->
                append("num_chapters_read", numChaptersRead.toString())
            }
            numVolumesRead?.let { numVolumesRead ->
                append("num_volumes_read", numVolumesRead.toString())
            }
            numWatchedEpisodes?.let { numWatchedEpisodes ->
                append("num_watched_episodes", numWatchedEpisodes.toString())
            }
        }))
        header("Authorization", "Bearer $token")
    }

    private suspend fun deleteListItem(type: String, id: Int) =
        client.delete<Unit>("https://api.myanimelist.net/v2/$type/$id/my_list_status")

    private suspend fun updateListStatus(
        type: String, id: Int, key: String, value: String, token: String,
    ) = client.patch<StandardListStatusResponse>(
        "https://api.myanimelist.net/v2/$type/$id/my_list_status"
    ) {
        setBody(FormDataContent(Parameters.build { append(key, value) }))
        header("Authorization", "Bearer $token")
    }
}