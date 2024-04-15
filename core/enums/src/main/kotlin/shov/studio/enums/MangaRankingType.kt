package shov.studio.enums

import androidx.annotation.StringRes

//Because of enums use for retrofit requests
@Suppress("EnumEntryName")
enum class MangaRankingType(@StringRes override val id: Int) : RankingType {
    all(R.string.all), manga(R.string.top_manga), novels(R.string.top_novels),
    oneshots(R.string.top_one_shots), doujin(R.string.top_doujinshi), manhwa(R.string.top_manhwa),
    manhua(R.string.top_manhua), bypopularity(R.string.most_popular),
    favorite(R.string.most_favorited),
}
