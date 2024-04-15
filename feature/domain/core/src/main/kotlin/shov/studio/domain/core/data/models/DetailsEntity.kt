package shov.studio.domain.core.data.models

import shov.studio.enums.MediaType
import shov.studio.enums.Season
import shov.studio.enums.DetailsStatus

data class DetailsEntity(
    val title: String = "",
    val picture: String? = null,
    val jaTitle: String = "",
    val startDate: String? = null,
    val endDate: String? = null,
    val synopsis: String = "",
    val rating: Float? = null,
    val rank: Int? = null,
    val popularity: Int? = null,
    val usersListing: Int? = null,
    val usersScoring: Int? = null,
    val mediaType: MediaType = MediaType.tv,
    val status: DetailsStatus = DetailsStatus.not_yet_aired,
    val genres: List<String> = emptyList(),
    val numEpisodes: Int? = null,
    val startYear: Int? = null,
    val startSeason: Season? = null,
    val numVolumes: Int? = null,
    val numChapters: Int? = null,
    val authors: List<AuthorEntity> = emptyList(),
    val pictures: List<String> = emptyList(),
    val relatedAnime: List<DetailsRelatedEntity> = emptyList(),
    val relatedManga: List<DetailsRelatedEntity> = emptyList(),
    val recommendations: List<DetailsRecommendationEntity> = emptyList(),
    val studios: List<String> = emptyList(),
    val statistic: StatusEntity? = null,
    val listStatus: ListStatusEntity? = null
)
