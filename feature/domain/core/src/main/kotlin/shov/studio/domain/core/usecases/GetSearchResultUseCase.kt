package shov.studio.domain.core.usecases

import androidx.paging.PagingData
import kotlinx.coroutines.flow.flowOf
import data.common.datas.DataStandardResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import core.utils.mapResponse
import javax.inject.Inject

class GetSearchResultUseCase @Inject constructor(
    private val standardRepository: StandardRepository
) {
    operator fun invoke(type: String, query: String) =
        if (query.replace(" ", "").length >= QUERY_MIN_LENGTH)
            standardRepository.search(type, query)
                .mapResponse(DataStandardResponse::toNodeStandardEntity)
        else flowOf(PagingData.empty())

    companion object {
        const val QUERY_MIN_LENGTH = 3
    }
}
