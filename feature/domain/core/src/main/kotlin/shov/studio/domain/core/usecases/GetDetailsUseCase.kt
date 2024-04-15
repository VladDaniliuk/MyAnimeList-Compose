package shov.studio.domain.core.usecases

import data.common.datas.DetailsResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toDetailsEntity
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(private val standardRepository: StandardRepository) {
    suspend operator fun invoke(type: String, id: Int) = standardRepository.details(type, id)
        .map(DetailsResponse::toDetailsEntity)
}
