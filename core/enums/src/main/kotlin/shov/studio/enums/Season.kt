package shov.studio.enums

import androidx.annotation.StringRes

//Because of enums use for retrofit requests
@Suppress("EnumEntryName")
enum class Season(@StringRes val id: Int) {
    winter(R.string.winter), spring(R.string.spring), summer(R.string.summer), fall(R.string.fall)
}
