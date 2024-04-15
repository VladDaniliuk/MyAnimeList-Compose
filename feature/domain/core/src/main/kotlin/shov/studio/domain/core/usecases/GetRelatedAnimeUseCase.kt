package shov.studio.domain.core.usecases

import data.common.datas.RelatedResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toRelatedEntity
import javax.inject.Inject

class GetRelatedAnimeUseCase @Inject constructor(
    private val standardRepository: StandardRepository,
) {
    suspend operator fun invoke(id: Int, type: String) = standardRepository
        .related(id, type).map(RelatedResponse::toRelatedEntity)
}
