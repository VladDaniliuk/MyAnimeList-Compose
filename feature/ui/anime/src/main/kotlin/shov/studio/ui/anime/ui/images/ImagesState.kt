package shov.studio.ui.anime.ui.images

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable

internal data class ImagesState @OptIn(ExperimentalFoundationApi::class) constructor(
    val uiState: ImagesUiState,
    val pagerState: PagerState,
    val onBackClick: () -> Unit
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun rememberImagesState(uiState: ImagesUiState, onBackClick: () -> Unit) = ImagesState(
    uiState,
    pagerState = rememberPagerState(uiState.index, pageCount = uiState.images::size),
    onBackClick
)
