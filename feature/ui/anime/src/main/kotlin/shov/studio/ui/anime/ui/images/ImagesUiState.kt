package shov.studio.ui.anime.ui.images

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

internal data class ImagesUiState(
    val images: ImmutableList<String> = persistentListOf(),
    val index: Int = 0,
    val status: LoadStatus = LoadStatus.INITIAL,
    val error: String? = null,
) {
    internal enum class LoadStatus {
        INITIAL, LOADING, ERROR, COMPLETE
    }
}
