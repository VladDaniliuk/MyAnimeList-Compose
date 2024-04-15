package shov.studio.enums

import androidx.annotation.StringRes

//Because of enums use for retrofit requests
@Suppress("EnumEntryName")
enum class RelationType(@StringRes val id: Int) {
    sequel(R.string.sequel), prequel(R.string.prequel),
    alternative_setting(R.string.alternative_setting),
    alternative_version(R.string.alternative_version), side_story(R.string.side_story),
    parent_story(R.string.parent_story), summary(R.string.summary), spin_off(R.string.spin_off),
    full_story(R.string.full_story), other(R.string.other), character(R.string.character)
}
