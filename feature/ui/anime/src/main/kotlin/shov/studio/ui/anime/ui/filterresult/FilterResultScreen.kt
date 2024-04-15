package shov.studio.ui.anime.ui.filterresult

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.common.AnimeItem
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.Event
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.State
import core.ui.common.loaderror.loadStateItem
import core.ui.common.scaffold.CustomScaffold

@Composable
internal fun FilterResultScreen(state: State, event: (Event) -> Unit) {
    val result = state.filterDetails.collectAsLazyPagingItems()

    CustomScaffold(
        onBackClick = { event(Event.OnBackClick) },
        title = stringResource(id = R.string.filter),
        actions = {
            OutlinedButton(
                modifier = Modifier.padding(end = 8.dp),
                onClick = { event(Event.OnBackClick) }
            ) {
                Text(text = stringResource(R.string.edit))
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = result.itemCount,
                key = result.itemKey(key = DetailsStandardModel::id),
                contentType = result.itemContentType()
            ) { index ->
                AnimeItem(
                    details = result[index],
                    onItemClick = { id -> event(Event.OnAnimeClick(id)) },
                    onAddToListClick = { id -> event(Event.OnAddToListClick(id)) },
                    isAuthorized = state.isAuthorized
                )
            }

            loadStateItem(
                combinedLoadStates = result.loadState,
                onRefresh = result::refresh,
                onRetry = result::retry
            )
        }
    }
}

@Preview
@Composable
private fun FilterResultPreview() {
    FilterResultScreen(
        state = State(
            true,
            flowOf(
                PagingData.from(
                    List(size = 10) { DetailsStandardModel(it, "Title", null, 0.0f, "Synopsis") },
                    LoadStates(
                        LoadState.NotLoading(true),
                        LoadState.NotLoading(true),
                        LoadState.NotLoading(true)
                    )
                )
            )
        )
    ) {}
}
