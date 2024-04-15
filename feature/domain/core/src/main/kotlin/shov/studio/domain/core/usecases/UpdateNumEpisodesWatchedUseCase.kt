package shov.studio.domain.core.usecases

import data.common.repositories.StandardUserRepository
import javax.inject.Inject

class UpdateNumEpisodesWatchedUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(id: Int, numVolumesRead: Int) = standardUserRepository
        .updateNumEpisodesWatched(id, numVolumesRead).map { response ->
            response.numEpisodesWatched
                ?: throw NullPointerException("num of episodes watched is null")
        }
}
