package shov.studio.domain.core.data.models

import data.common.datas.StandardListStatusResponse

fun StandardListStatusResponse.toEntity() = ListStatusEntity(
    status = status,
    score = score,
    numChaptersRead = numChaptersRead,
    numEpisodesWatched = numEpisodesWatched,
    numVolumesRead = numVolumesRead,
    isRereading = isRereading,
    tags = tags,
    comments = comments
)
