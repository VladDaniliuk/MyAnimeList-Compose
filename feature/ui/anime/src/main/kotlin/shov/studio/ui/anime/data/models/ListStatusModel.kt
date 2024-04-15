package shov.studio.ui.anime.data.models

import shov.studio.enums.Status

internal data class ListStatusModel(
    val status: Status,
    val score: Int,
    val numVolumesRead: Int?,
    val numChaptersRead: Int?,
    val numEpisodesWatched: Int?,
    val isRewatching: Boolean,
    val tags: List<String>,
    val comments: String,
) {
    constructor(isAnime: Boolean) : this(
        status = if (isAnime) Status.not_watching else Status.not_reading,
        tags = emptyList(),
        score = 0,
        numVolumesRead = if (isAnime) null else 0,
        numChaptersRead = if (isAnime) null else 0,
        numEpisodesWatched = if (isAnime) 0 else null,
        isRewatching = false,
        comments = ""
    )
}

internal fun ListStatusModel?.copy(
    isAnime: Boolean,
    status: Status = this?.status ?: if (isAnime) Status.watching else Status.not_reading,
    score: Int = this?.score ?: 0,
    numVolumesRead: Int? = this?.numVolumesRead ?: if (isAnime) null else 0,
    numChaptersRead: Int? = this?.numChaptersRead ?: if (isAnime) null else 0,
    numEpisodesWatched: Int? = this?.numEpisodesWatched ?: if (isAnime) 0 else null,
    isRewatching: Boolean = this?.isRewatching ?: false,
    tags: List<String> = this?.tags ?: emptyList(),
    comments: String? = this?.comments ?: "",
) = this?.copy(
    status = status,
    tags = tags,
    score = score,
    numVolumesRead = numVolumesRead,
    numChaptersRead = numChaptersRead,
    numEpisodesWatched = numEpisodesWatched,
    isRewatching = isRewatching,
    comments = comments ?: ""
) ?: ListStatusModel(
    status = status,
    tags = tags,
    score = score,
    numVolumesRead = numVolumesRead,
    numChaptersRead = numChaptersRead,
    numEpisodesWatched = numEpisodesWatched,
    isRewatching = isRewatching,
    comments = comments ?: ""
)
