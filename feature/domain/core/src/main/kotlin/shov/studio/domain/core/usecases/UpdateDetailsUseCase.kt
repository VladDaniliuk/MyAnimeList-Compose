package shov.studio.domain.core.usecases

import shov.studio.enums.Status
import data.common.repositories.StandardUserRepository
import javax.inject.Inject

class UpdateDetailsUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(
        type: String, id: Int, status: Status, score: Int, tags: List<String>, comment: String,
        isRewatching: Boolean, numChaptersRead: Int?, numVolumesRead: Int?,
        numWatchedEpisodes: Int?,
    ) = standardUserRepository.updateDetails(
        type, id, status, score, tags, comment, isRewatching, numChaptersRead, numVolumesRead,
        numWatchedEpisodes
    )
}
