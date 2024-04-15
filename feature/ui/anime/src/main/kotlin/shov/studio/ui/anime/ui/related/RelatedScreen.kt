package shov.studio.ui.anime.ui.related

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.common.AnimeItem
import shov.studio.ui.anime.ui.related.RelatedContract.Event
import shov.studio.ui.anime.ui.related.RelatedContract.State
import core.ui.common.scaffold.CustomScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RelatedScreen(
    state: State,
    pullToRefreshState: PullToRefreshState,
    event: (Event) -> Unit,
) {
    CustomScaffold(
        onBackClick = { event(Event.OnBackClick) },
        title = stringResource(
            if (state.type == AnimeRoute.Type.anime) R.string.related_animes
            else R.string.read_mangas
        ),
        snackbarHostState = state.snackbarHostState
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(top = paddingValues.calculateTopPadding())
                .fillMaxSize()
                .nestedScroll(pullToRefreshState.nestedScrollConnection)
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding()),
            ) {
                items(state.related, DetailsStandardModel::id) { model ->
                    AnimeItem(
                        details = model,
                        isLoading = model.title == "",
                        onAddToListClick = { id -> event(Event.OnAddToListClick(id, state.type)) },
                        onItemClick = { id -> event(Event.OnItemClick(id)) },
                        isAuthorized = state.isAuthorized
                    )
                }
            }

            if (state.isErrorVisible) Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = state.error!!, textAlign = TextAlign.Center)

                Button(onClick = { event(Event.OnRetryClick) }) {
                    Text(text = stringResource(R.string.retry))
                }
            }

            PullToRefreshContainer(
                modifier = Modifier.align(Alignment.TopCenter),
                state = pullToRefreshState,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun RelatedPreview() {
    RelatedScreen(
        state = State(AnimeRoute.Type.anime),
        pullToRefreshState = rememberPullToRefreshState()
    ) {}
}
