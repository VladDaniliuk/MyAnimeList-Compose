package shov.studio.domain.core.usecases

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import shov.studio.enums.Sort
import shov.studio.enums.Status
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import javax.inject.Inject

class GetUserListsPreviewUseCase @Inject constructor(
    private val standardRepository: StandardRepository,
) {
    suspend operator fun invoke() = coroutineScope {
        fun getListPreview(type: String, status: Status, sort: Sort) = async {
            standardRepository.userListPreview(type, status, sort)
                .map { standard -> standard.data.map { data -> data.toNodeStandardEntity() } }
        }

        val anime = getListPreview("anime", Status.watching, Sort.list_updated_at)
        val manga = getListPreview("manga", Status.reading, Sort.list_updated_at)

        awaitAll(anime, manga)
    }
}
