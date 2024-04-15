package shov.studio.domain.core.data.models

import shov.studio.enums.Status

data class ListStatusEntity(
    val status: Status?,
    val score: Int,
    val numChaptersRead: Int?,
    val numEpisodesWatched: Int?,
    val numVolumesRead: Int?,
    val isRereading: Boolean,
    val tags: List<String>,
    val comments: String,
)
