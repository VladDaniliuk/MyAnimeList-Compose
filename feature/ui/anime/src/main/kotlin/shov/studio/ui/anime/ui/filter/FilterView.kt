package shov.studio.ui.anime.ui.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.enums.Season
import shov.studio.enums.SortType
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.filter.ui.FilterButtonRow
import core.ui.common.scaffold.CustomScaffold

@Composable
internal fun FilterView(
    uiState: FilterUiState,
    modifier: Modifier = Modifier,
    setYear: (String) -> Unit = {},
    onYearSelected: () -> Unit = {},
    onSeasonSelected: (Season) -> Unit = {},
    onSortSelected: (SortType?) -> Unit = {},
    onSeasonExpandedChange: (Boolean) -> Unit = {},
    onSortExpandedChange: (Boolean) -> Unit = {},
    onBackClick: () -> Unit = {},
    onReset: () -> Unit = {},
    onConfirm: () -> Unit = {},
) {
    CustomScaffold(
        modifier = modifier,
        onBackClick = onBackClick,
        title = stringResource(R.string.filter),
        snackbarHostState = uiState.snackbarHostState,
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            FilterContentView(
                year = uiState.year,
                season = uiState.season,
                sort = uiState.sort,
                isSeasonExpanded = uiState.isSeasonExpanded,
                isSortExpanded = uiState.isSortExpanded,
                seasonFocusRequester = uiState.seasonFocusRequester,
                setYear = setYear,
                onYearSelected = onYearSelected,
                onSeasonSelected = onSeasonSelected,
                onSortSelected = onSortSelected,
                onSeasonExpandedChange = onSeasonExpandedChange,
                onSortExpandedChange = onSortExpandedChange
            )

            FilterButtonRow(onReset = onReset, onConfirm = onConfirm)
        }
    }
}

@Preview
@Composable
private fun FilterPreview() {
    FilterView(
        uiState = FilterUiState(
            year = "2009",
            season = Season.winter,
            sort = SortType.anime_num_list_users
        )
    )
}
