package shov.studio.ui.anime.data.models

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import shov.studio.enums.MediaType
import shov.studio.enums.RelationType
import shov.studio.enums.Season
import shov.studio.enums.DetailsStatus
import shov.studio.ui.anime.data.converters.AuthorModel

internal interface DetailsModel {
    val title: String
    val picture: String?
    val rating: Float?
}

internal data class DetailsRelatedModel(
    val id: Int,
    override val title: String,
    override val picture: String?,
    val startYear: Int?,
    val startSeason: Season?,
    override val rating: Float?,
    val relationType: RelationType,
) : DetailsModel

internal data class DetailsStandardModel(
    val id: Int,
    override val title: String,
    override val picture: String?,
    override val rating: Float?,
    val synopsis: String,
) : DetailsModel

internal data class DetailsFullModel(
    override val title: String = "",
    override val picture: String? = null,
    val jaTitle: String = "",
    val startDate: String? = "",
    val endDate: String? = "",
    val synopsis: String = "",
    override val rating: Float? = null,
    val rank: Int? = null,
    val popularity: Int? = null,
    val usersListing: Int? = null,
    val usersScoring: Int? = null,
    val mediaType: MediaType = MediaType.tv,
    val status: DetailsStatus = DetailsStatus.not_yet_aired,
    val genres: ImmutableList<String> = persistentListOf(),
    val numVolumes: Int? = null,
    val numChapters: Int? = null,
    val numEpisodes: Int? = null,
    val startYear: Int? = null,
    val startSeason: Season? = null,
    val pictures: ImmutableList<String> = persistentListOf(),
    val relatedAnime: ImmutableList<DetailsRelatedModel> = persistentListOf(),
    val relatedManga: ImmutableList<DetailsRelatedModel> = persistentListOf(),
    val recommendations: ImmutableList<RecommendationModel> = persistentListOf(),
    val authors: ImmutableList<AuthorModel> = persistentListOf(),
    val studios: ImmutableList<String> = persistentListOf(),
    val statistic: StatusModel? = null,
    val listStatus: ListStatusModel = ListStatusModel(isAnime = !((numEpisodes == null) and ((numChapters != null) or (numEpisodes != null)))),
) : DetailsModel {
    constructor(type: String) : this(listStatus = ListStatusModel(isAnime = type == "anime"))
}
