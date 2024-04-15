package shov.studio.ui.anime.ui.filter

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import shov.studio.enums.Season
import shov.studio.enums.SortType
import shov.studio.ui.anime.R

@Composable
internal fun FilterScreen(
    onBackClick: () -> Unit,
    viewModel: FilterViewModel = hiltViewModel(),
    onFilterClick: (year: String, season: Season, score: SortType) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val yearError = stringResource(R.string.please_select_year)
    val seasonError = stringResource(R.string.please_select_season)

    FilterView(
        uiState = uiState,
        setYear = viewModel::onYearChanged,
        onYearSelected = { viewModel.onYearSelected(focusManager::clearFocus) },
        onSeasonSelected = viewModel::onSeasonSelected,
        onSortSelected = viewModel::onSortSelected,
        onSeasonExpandedChange = viewModel::onSeasonExpanded,
        onSortExpandedChange = viewModel::onSortExpanded,
        onBackClick = onBackClick,
        onReset = viewModel::onReset
    ) {
        viewModel.onConfirm(yearError, seasonError, onFilterClick)
    }
}

@Preview
@Composable
private fun FilterScreenPreview() {
    FilterView(
        uiState = FilterUiState(
            year = "2009",
            season = Season.winter,
            sort = SortType.anime_num_list_users
        )
    )
}
