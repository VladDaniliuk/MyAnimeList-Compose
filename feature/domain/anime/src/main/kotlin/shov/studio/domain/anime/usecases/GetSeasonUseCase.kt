package shov.studio.domain.anime.usecases

import shov.studio.enums.Season
import shov.studio.enums.SortType
import shov.studio.data.anime.repositories.AnimeRepository
import data.common.datas.DataStandardResponse
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import core.utils.mapResponse
import javax.inject.Inject

class GetSeasonUseCase @Inject constructor(
    private val animeRepository: AnimeRepository,
) {
    operator fun invoke(year: Int, season: Season, sort: SortType) =
        animeRepository.getSeason(year, season, sort)
            .mapResponse(DataStandardResponse::toNodeStandardEntity)
}
