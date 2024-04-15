package shov.studio.domain.core.usecases

import data.common.datas.StandardListStatusResponse
import data.common.repositories.StandardUserRepository
import shov.studio.domain.core.data.models.toEntity
import javax.inject.Inject

class UpdateIsRewatchingUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(type: String, id: Int, isRewatching: Boolean) =
        standardUserRepository.updateIsRewatching(type, id, isRewatching)
            .map(StandardListStatusResponse::toEntity)
}
