package shov.studio.ui.anime.ui.filter

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.focus.FocusRequester
import shov.studio.enums.Season
import shov.studio.enums.SortType

internal data class FilterUiState(
    val year: String = "",
    val season: Season? = null,
    val isSeasonExpanded: Boolean = false,
    val seasonFocusRequester: FocusRequester = FocusRequester(),
    val sort: SortType? = null,
    val isSortExpanded: Boolean = false,
    val snackbarHostState: SnackbarHostState = SnackbarHostState(),
)
