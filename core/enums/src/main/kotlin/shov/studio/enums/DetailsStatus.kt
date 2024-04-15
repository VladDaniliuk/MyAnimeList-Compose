package shov.studio.enums

import androidx.annotation.StringRes

//Because of enums use for retrofit requests
@Suppress("EnumEntryName")
enum class DetailsStatus(@StringRes val id: Int) {
    finished_airing(R.string.finished_airing), currently_airing(R.string.currently_airing),
    not_yet_aired(R.string.not_yet_aired), finished(R.string.finished),
    currently_publishing(R.string.currently_publishing),
    not_yet_published(R.string.not_yet_published), on_hiatus(R.string.on_hiatus)
}
