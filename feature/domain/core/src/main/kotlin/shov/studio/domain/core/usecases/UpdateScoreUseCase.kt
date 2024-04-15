package shov.studio.domain.core.usecases

import data.common.datas.StandardListStatusResponse
import data.common.repositories.StandardUserRepository
import shov.studio.domain.core.data.models.toEntity
import javax.inject.Inject

class UpdateScoreUseCase @Inject constructor(
    private val standardUserRepository: StandardUserRepository,
) {
    suspend operator fun invoke(type: String, id: Int, score: Int) = standardUserRepository
        .updateScore(type, id, score).map(StandardListStatusResponse::toEntity)
}
