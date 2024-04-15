package shov.studio.domain.anime.usecases

import shov.studio.enums.AnimeRankingType
import data.common.datas.DataStandardResponse
import data.common.repositories.StandardRepository
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import core.utils.mapResponse
import javax.inject.Inject

class GetAnimeRankingAnimeUseCase @Inject constructor(
    private val standardRepository: StandardRepository,
) {
    operator fun invoke(type: String) = AnimeRankingType.entries.map { rankingType ->
        standardRepository.rankingList(type, rankingType)
            .mapResponse(DataStandardResponse::toNodeStandardEntity)
    }
}