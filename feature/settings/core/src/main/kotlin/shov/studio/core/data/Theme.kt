package shov.studio.core.data

import androidx.annotation.StringRes
import shov.studio.core.R

enum class Theme(@StringRes val id: Int) {
    DARK(R.string.dark), LIGHT(R.string.light), DEFAULT(R.string.default_theme)
}
