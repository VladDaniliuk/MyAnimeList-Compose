package shov.studio.ui.anime.ui.userlist.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Sort
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.collections.immutable.persistentListOf
import shov.studio.enums.Sort
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.common.AnimeItem
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.Event
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.State
import core.ui.common.loaderror.loadStateItem
import core.ui.common.paging.rememberLazyListState
import core.ui.common.scaffold.CustomScaffold

@Composable
internal fun UserListDetailsScreen(state: State, event: (Event) -> Unit) {
    CustomScaffold(
        title = stringResource(
            if (state.type == AnimeRoute.Type.anime) R.string.user_anime_list
            else R.string.user_manga_list
        ),
        onBackClick = { event(Event.OnBackClick) },
        actions = {
            IconButton(onClick = { event(Event.OnSortClick) }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.Sort,
                    contentDescription = Icons.AutoMirrored.Rounded.Sort.name
                )
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            val pagerState = rememberPagerState { 5 }

            UserListScrollableTabRow(pagerState = pagerState, type = state.type)

            val pages = persistentListOf(
                state.watching.collectAsLazyPagingItems(),
                state.completed.collectAsLazyPagingItems(),
                state.hold.collectAsLazyPagingItems(),
                state.dropped.collectAsLazyPagingItems(),
                state.plan.collectAsLazyPagingItems()
            )

            HorizontalPager(state = pagerState) { index ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = pages[index].rememberLazyListState(),
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        count = pages[index].itemCount,
                        key = pages[index].itemKey(DetailsStandardModel::id),
                        contentType = pages[index].itemContentType()
                    ) { anime ->
                        AnimeItem(
                            details = pages[index][anime],
                            onItemClick = { id -> event(Event.OnItemClick(id)) },
                            onAddToListClick = { id -> event(Event.OnAddToListClick(id)) },
                            isAuthorized = state.isAuthorized
                        )
                    }

                    loadStateItem(
                        combinedLoadStates = pages[index].loadState,
                        onRefresh = pages[index]::refresh,
                        onRetry = pages[index]::retry
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserListDetailsPreview() {
    UserListDetailsScreen(state = State(type = AnimeRoute.Type.anime, sort = Sort.list_score)) {}
}
