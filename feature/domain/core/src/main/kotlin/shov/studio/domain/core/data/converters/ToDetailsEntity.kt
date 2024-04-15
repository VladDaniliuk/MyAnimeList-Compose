package shov.studio.domain.core.data.converters

import data.common.datas.AuthorResponse
import data.common.datas.DataRecommendationResponse
import data.common.datas.DataRelatedResponse
import data.common.datas.DetailsResponse
import data.common.datas.GenreResponse
import data.common.datas.MainPictureMediumResponse
import shov.studio.domain.core.data.models.DetailsEntity
import shov.studio.domain.core.data.models.toEntity

internal fun DetailsResponse.toDetailsEntity() = DetailsEntity(
    title = title,
    picture = mainPicture?.medium,
    jaTitle = alternativeTitles.ja,
    startDate = startDate,
    endDate = endDate,
    synopsis = synopsis,
    rating = mean,
    rank = rank,
    popularity = popularity,
    usersListing = numListUsers,
    usersScoring = numScoringUsers,
    mediaType = mediaType,
    status = status,
    genres = genres?.map(GenreResponse::name) ?: emptyList(),
    numVolumes = numVolumes,
    numChapters = numChapters,
    numEpisodes = numEpisodes,
    startSeason = startSeason?.season,
    startYear = startSeason?.year,
    authors = authors?.map(AuthorResponse::toAuthorEntity) ?: emptyList(),
    pictures = pictures.map(MainPictureMediumResponse::medium),
    relatedAnime = relatedAnime.map(DataRelatedResponse::toDetailsRelatedEntity),
    relatedManga = relatedManga.map(DataRelatedResponse::toDetailsRelatedEntity),
    recommendations = recommendations
        .map(DataRecommendationResponse::toDetailsRecommendationEntity),
    studios = studios?.map(GenreResponse::name) ?: emptyList(),
    statistic = statistics?.status?.toStatusEntity(),
    listStatus = listStatusResponse?.toEntity()
)
