package shov.studio.ui.anime.ui.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import shov.studio.enums.Season
import shov.studio.enums.SortType
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.filter.ui.FilterExposedDropdownMenuBox
import shov.studio.ui.anime.ui.filter.ui.YearFilterTextField

@Composable
internal fun ColumnScope.FilterContentView(
    year: String,
    season: Season?,
    sort: SortType?,
    modifier: Modifier = Modifier,
    isSeasonExpanded: Boolean = false,
    isSortExpanded: Boolean = false,
    seasonFocusRequester: FocusRequester = FocusRequester(),
    setYear: (String) -> Unit = {},
    onYearSelected: () -> Unit = {},
    onSeasonSelected: (Season) -> Unit = {},
    onSortSelected: (SortType?) -> Unit = {},
    onSeasonExpandedChange: (Boolean) -> Unit = {},
    onSortExpandedChange: (Boolean) -> Unit = {},
) {
    Column(
        modifier = modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        YearFilterTextField(year, setYear, onYearSelected)

        FilterExposedDropdownMenuBox(
            modifier = Modifier.focusRequester(seasonFocusRequester),
            expanded = isSeasonExpanded,
            onExpandedChange = onSeasonExpandedChange,
            value = season?.let { stringResource(it.id) } ?: "",
            onSelected = onSeasonSelected,
            displayedText = stringResource(R.string.season),
            entries = persistentListOf(
                stringResource(Season.winter.id) to Season.winter,
                stringResource(Season.spring.id) to Season.spring,
                stringResource(Season.summer.id) to Season.summer,
                stringResource(Season.fall.id) to Season.fall
            )
        )

        FilterExposedDropdownMenuBox(
            expanded = isSortExpanded,
            onExpandedChange = onSortExpandedChange,
            value = stringResource(id = sort?.id ?: R.string.default_value),
            onSelected = onSortSelected,
            displayedText = stringResource(R.string.sort),
            entries = persistentListOf(
                stringResource(R.string.default_value) to null,
                stringResource(SortType.anime_score.id) to SortType.anime_score,
                stringResource(SortType.anime_num_list_users.id) to SortType.anime_num_list_users
            )
        )
    }
}

@Preview
@Composable
private fun FilterContentPreview() {
    Column {
        FilterContentView("2029", Season.winter, SortType.anime_score)
    }
}
