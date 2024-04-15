package shov.studio.domain.manga.usecases

import shov.studio.enums.MangaRankingType
import data.common.datas.DataStandardResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import core.utils.mapResponse
import javax.inject.Inject

class GetMangaRankingAnimeUseCase @Inject constructor(
    private val standardRepository: StandardRepository
) {
    operator fun invoke(type: String) = MangaRankingType.entries.map { mangaRankingType ->
        standardRepository.rankingList(type, mangaRankingType)
            .mapResponse(DataStandardResponse::toNodeStandardEntity)
    }
}