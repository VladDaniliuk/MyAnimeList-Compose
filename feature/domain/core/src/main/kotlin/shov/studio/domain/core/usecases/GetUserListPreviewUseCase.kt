package shov.studio.domain.core.usecases

import shov.studio.enums.Sort
import shov.studio.enums.Status
import data.common.datas.DataStandardResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import javax.inject.Inject

class GetUserListPreviewUseCase @Inject constructor(
    private val standardRepository: StandardRepository,
) {
    suspend operator fun invoke(type: String, status: Status, sort: Sort) = standardRepository
        .userListPreview(type, status, sort).map { response ->
            response.data.map(DataStandardResponse::toNodeStandardEntity)
        }
}
