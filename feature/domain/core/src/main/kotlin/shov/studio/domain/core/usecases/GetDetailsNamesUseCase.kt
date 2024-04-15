package shov.studio.domain.core.usecases

import data.common.datas.DetailsNamesResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toDetailsNamesEntity
import javax.inject.Inject

class GetDetailsNamesUseCase @Inject constructor(
    private val standardRepository: StandardRepository,
) {
    suspend operator fun invoke(type: String, id: Int) = standardRepository
        .detailsNames(type, id).map(DetailsNamesResponse::toDetailsNamesEntity)
}
