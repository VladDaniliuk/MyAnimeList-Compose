package shov.studio.ui.anime.data.converters

import kotlinx.collections.immutable.toImmutableList
import shov.studio.domain.core.data.models.AuthorEntity
import shov.studio.domain.core.data.models.DetailsEntity
import shov.studio.ui.anime.data.models.DetailsFullModel
import shov.studio.ui.anime.data.models.DetailsRelatedModel
import shov.studio.ui.anime.data.models.RecommendationModel
import shov.studio.ui.anime.data.models.StatusModel
import shov.studio.ui.anime.data.models.toModel

internal fun DetailsEntity.toDetailsModel(isAnime:Boolean) = DetailsFullModel(
    title = title,
    picture = picture,
    jaTitle = jaTitle,
    startDate = startDate,
    endDate = endDate,
    synopsis = synopsis,
    rating = rating,
    rank = rank,
    popularity = popularity,
    usersListing = usersListing,
    usersScoring = usersScoring,
    mediaType = mediaType,
    status = status,
    genres = genres.toImmutableList(),
    numVolumes = numVolumes,
    numChapters = numChapters,
    pictures = pictures.toImmutableList(),
    relatedAnime = relatedAnime.map { entity ->
        DetailsRelatedModel(
            id = entity.id,
            title = entity.title,
            picture = entity.picture,
            startYear = entity.startYear,
            startSeason = entity.startSeason,
            rating = entity.rating,
            relationType = entity.relationType
        )
    }.toImmutableList(),
    relatedManga = relatedManga.map { entity ->
        DetailsRelatedModel(
            id = entity.id,
            title = entity.title,
            picture = entity.picture,
            startYear = entity.startYear,
            startSeason = entity.startSeason,
            rating = entity.rating,
            relationType = entity.relationType
        )
    }.toImmutableList(),
    recommendations = recommendations.map { entity ->
        RecommendationModel(entity.id, entity.title, entity.picture)
    }.toImmutableList(),
    authors = authors.map(AuthorEntity::toAuthorModel).toImmutableList(),
    startSeason = startSeason,
    numEpisodes = numEpisodes,
    startYear = startYear,
    statistic = statistic?.let { statistic ->
        StatusModel(
            watching = statistic.watching,
            completed = statistic.completed,
            onHold = statistic.onHold,
            dropped = statistic.dropped,
            planToWatch = statistic.planToWatch
        )
    },
    studios = studios.toImmutableList(),
    listStatus = listStatus.toModel(isAnime)
)
