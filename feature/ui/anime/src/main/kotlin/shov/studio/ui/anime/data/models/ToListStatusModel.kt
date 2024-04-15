package shov.studio.ui.anime.data.models

import shov.studio.enums.Status
import shov.studio.domain.core.data.models.ListStatusEntity

internal fun ListStatusEntity?.toModel(isAnime: Boolean) = ListStatusModel(
    status = this?.status ?: if (isAnime) Status.not_watching else Status.not_reading,
    score = this?.score ?: 0,
    numVolumesRead = this?.numVolumesRead,
    numChaptersRead = this?.numChaptersRead,
    numEpisodesWatched = this?.numEpisodesWatched,
    isRewatching = this?.isRereading ?: false,
    tags = this?.tags ?: emptyList(),
    comments = this?.comments ?: ""
)
