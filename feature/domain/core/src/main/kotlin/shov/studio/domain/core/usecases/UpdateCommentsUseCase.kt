package shov.studio.domain.core.usecases

import data.common.datas.StandardListStatusResponse
import data.common.repositories.StandardUserRepository
import shov.studio.domain.core.data.models.ListStatusEntity
import shov.studio.domain.core.data.models.toEntity
import javax.inject.Inject

class UpdateCommentsUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(type: String, id: Int, comments: String): Result<ListStatusEntity> =
        standardUserRepository.updateComments(type, id, comments)
            .map(StandardListStatusResponse::toEntity)
}
