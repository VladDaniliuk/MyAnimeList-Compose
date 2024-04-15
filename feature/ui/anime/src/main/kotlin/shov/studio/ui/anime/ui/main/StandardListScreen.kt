package shov.studio.ui.anime.ui.main

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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import shov.studio.enums.AnimeRankingType
import core.ui.common.loaderror.loadStateItem
import core.ui.common.paging.rememberLazyListState
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.common.AnimeItem
import shov.studio.ui.anime.ui.main.StandardListContract.Event
import shov.studio.ui.anime.ui.main.StandardListContract.State
import shov.studio.ui.anime.ui.utils.collectAsListOfLazyPagingItems

@Composable
internal fun StandardListScreen(
    searchDetails: LazyPagingItems<DetailsStandardModel>,
    state: State,
    event: (Event) -> Unit,
) {
    Scaffold(topBar = { CustomSearchBar(state, event, searchDetails) }) { paddingValues ->
        Column(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {
            val pagerState = rememberPagerState { 9 }
            val coroutineScope = rememberCoroutineScope()
            val detailsPagingList = state.detailsPagingList.collectAsListOfLazyPagingItems()

            MainScrollableTabRow(
                pagerState = pagerState,
                tabs = AnimeRankingType.entries.toImmutableList(),
                onTabClick = { page ->
                    coroutineScope.launch { pagerState.animateScrollToPage(page) }
                }
            )

            HorizontalPager(state = pagerState) { page ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = detailsPagingList[page].rememberLazyListState(),
                    contentPadding = PaddingValues(
                        bottom = WindowInsets.navigationBars.asPaddingValues()
                            .calculateBottomPadding()
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        count = detailsPagingList[page].itemCount,
                        key = detailsPagingList[page].itemKey(DetailsStandardModel::id),
                        contentType = detailsPagingList[page].itemContentType()
                    ) { anime ->
                        AnimeItem(
                            details = detailsPagingList[page][anime],
                            onItemClick = { id -> event(Event.OnItemClick(id)) },
                            onAddToListClick = { id ->
                                event(Event.OnAddToListClick(id, state.type))
                            },
                            isAuthorized = state.isAuthorized
                        )
                    }

                    loadStateItem(
                        combinedLoadStates = detailsPagingList[page].loadState,
                        onRefresh = detailsPagingList[page]::refresh,
                        onRetry = detailsPagingList[page]::retry
                    )
                }
            }
        }
    }
}
