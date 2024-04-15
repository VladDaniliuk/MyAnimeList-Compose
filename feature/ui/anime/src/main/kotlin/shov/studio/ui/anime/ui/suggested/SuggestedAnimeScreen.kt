package shov.studio.ui.anime.ui.suggested

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.common.AnimeItem
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.Event
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.State
import core.ui.common.loaderror.loadStateItem
import core.ui.common.scaffold.CustomScaffold

@Composable
internal fun SuggestedAnimeScreen(state: State, event: (Event) -> Unit) {
    val items = state.animes.collectAsLazyPagingItems()

    CustomScaffold(title = stringResource(R.string.suggested_animes)) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                count = items.itemCount,
                key = items.itemKey(key = DetailsStandardModel::id),
                contentType = items.itemContentType()
            ) { index ->
                AnimeItem(
                    details = items[index],
                    onItemClick = { id -> event(Event.OnAnimeClick(id)) },
                    onAddToListClick = { id -> event(Event.OnAddToListClick(id)) },
                    isAuthorized = state.isAuthorized
                )
            }

            loadStateItem(
                combinedLoadStates = items.loadState,
                onRefresh = items::refresh,
                onRetry = items::retry
            )
        }
    }
}

@Preview
@Composable
private fun SuggestedAnimePreview() {
    SuggestedAnimeScreen(
        state = State(
            flowOf(
                PagingData.from(
                    (1..10).map { id ->
                        DetailsStandardModel(
                            id,
                            "Long Long Long Long Long Long Long Long Long Long Long Long",
                            "https://myanimelist.cdn-dena.com/images/anime/3/87463l.jpg",
                            10f,
                            "Long Long Long Long Long Long Long Long Long Long Long Long "
                        )
                    }
                )
            )
        )
    ) {}
}
