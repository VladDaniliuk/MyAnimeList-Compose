package shov.studio.enums

import androidx.annotation.StringRes

//Because of enums use for retrofit requests
@Suppress("EnumEntryName")
enum class AnimeRankingType(@StringRes override val id: Int) : RankingType {
    all(R.string.all), airing(R.string.airing), upcoming(R.string.upcoming), tv(R.string.tv),
    ova(R.string.ova), movie(R.string.movie), special(R.string.special),
    bypopularity(R.string.bypopularity), favorite(R.string.favorite)
}
