package shov.studio.domain.core.usecases

import shov.studio.enums.Status
import data.common.repositories.StandardUserRepository
import javax.inject.Inject

class UpdateStatusUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(type: String, id: Int, status: Status) =
        standardUserRepository.updateStatus(type, id, status)
}
