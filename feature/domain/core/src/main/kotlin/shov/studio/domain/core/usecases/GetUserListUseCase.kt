package shov.studio.domain.core.usecases

import shov.studio.enums.Sort
import shov.studio.enums.Status
import data.common.datas.DataStandardResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import core.utils.mapResponse
import javax.inject.Inject

class GetUserListUseCase @Inject constructor(private val standardRepository: StandardRepository) {
    operator fun invoke(type: String, status: Status, sort: Sort) = standardRepository
        .userList(type, status, sort).mapResponse(DataStandardResponse::toNodeStandardEntity)
}
