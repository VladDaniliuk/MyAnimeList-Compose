package shov.studio.domain.anime.usecases

import core.utils.mapResponse
import shov.studio.data.anime.repositories.AnimeRepository
import data.common.datas.DataStandardResponse
import shov.studio.domain.core.data.converters.toNodeStandardEntity
import javax.inject.Inject


class GetSuggestedAnimesUseCase @Inject constructor(private val animeRepository: AnimeRepository) {
    operator fun invoke() =
        animeRepository.getSuggested().mapResponse(DataStandardResponse::toNodeStandardEntity)
}
