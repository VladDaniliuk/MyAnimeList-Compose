package shov.studio.domain.core.usecases

import data.common.repositories.StandardUserRepository
import javax.inject.Inject

class UpdateNumVolumesReadUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(id: Int, numVolumesRead: Int) = standardUserRepository
        .updateNumVolumesRead(id, numVolumesRead).map { response ->
            response.numVolumesRead ?: throw NullPointerException("num of volumes read is null")
        }
}
