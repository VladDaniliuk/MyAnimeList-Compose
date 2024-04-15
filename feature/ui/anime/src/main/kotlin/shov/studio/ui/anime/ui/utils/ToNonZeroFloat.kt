package shov.studio.ui.anime.ui.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

internal fun Int.toNonZeroFloat() = if (this != 0) toFloat() else Float.MinProgress

@Immutable
internal class Float private constructor() {
    companion object {
        @Stable
        val MinProgress = 0.1f
    }
}
