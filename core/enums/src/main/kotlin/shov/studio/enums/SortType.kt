package shov.studio.enums

import androidx.annotation.StringRes

//Because of enums use for retrofit requests
@Suppress("EnumEntryName")
enum class SortType(@StringRes val id: Int) {
    anime_score(R.string.score), anime_num_list_users(R.string.user_list)
}
