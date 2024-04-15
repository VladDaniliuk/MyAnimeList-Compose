package shov.studio.domain.core.usecases

import data.common.datas.StandardListStatusResponse
import data.common.repositories.StandardUserRepository
import shov.studio.domain.core.data.models.toEntity
import javax.inject.Inject

class UpdateTagsUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(type: String, id: Int, tags: List<String>) =
        standardUserRepository.updateTags(type, id, tags).map(StandardListStatusResponse::toEntity)
}
