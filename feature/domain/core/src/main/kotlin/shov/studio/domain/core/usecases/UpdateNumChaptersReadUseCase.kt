package shov.studio.domain.core.usecases

import data.common.repositories.StandardUserRepository
import javax.inject.Inject

class UpdateNumChaptersReadUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(id: Int, numChaptersRead: Int) = standardUserRepository
        .updateNumChaptersRead(id, numChaptersRead).map { response ->
            response.numChaptersRead ?: throw NullPointerException("num of chapters read is null")
        }
}
