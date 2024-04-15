package shov.studio.ui.anime.ui.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.common.AnimeItem
import shov.studio.ui.anime.ui.main.StandardListContract.Event
import shov.studio.ui.anime.ui.main.StandardListContract.State
import core.ui.common.button.AnimatedIconButton
import core.ui.common.loaderror.loadStateItem

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun CustomSearchBar(
    state: State,
    event: (Event) -> Unit,
    searchDetails: LazyPagingItems<DetailsStandardModel>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchBar(
            query = state.query,
            onQueryChange = { query -> event(Event.OnQueryChange(query)) },
            onSearch = {},
            active = state.isSearchBarActive,
            onActiveChange = { isActive -> event(Event.OnSearchBarClick(isActive)) },
            placeholder = { Text("Search ${state.type}") },
            leadingIcon = {
                AnimatedIconButton(
                    isVisible = state.isSearchBarActive,
                    icon = Icons.AutoMirrored.Rounded.ArrowBack
                ) {
                    event(Event.OnQueryChange(""))
                    event(Event.OnSearchBarClick(false))
                }
            },
            trailingIcon = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AnimatedIconButton(
                        isVisible = state.query.isNotEmpty(),
                        icon = Icons.Rounded.Clear,
                        onClick = { event(Event.OnQueryChange("")) }
                    )

                    AnimatedIconButton(
                        isVisible = state.isSearchBarActive,
                        icon = Icons.Rounded.Tune,
                        onClick = { event(Event.OnFilterClick) }
                    )

                    AnimatedIconButton(
                        isVisible = state.isSearchBarActive.not(),
                        icon = Icons.Rounded.Settings,
                        onClick = { event(Event.OnSettingsClick) }
                    )
                }
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    count = searchDetails.itemCount,
                    key = searchDetails.itemKey(),
                    contentType = searchDetails.itemContentType()
                ) { index ->
                    AnimeItem(
                        details = searchDetails[index],
                        onItemClick = { id -> event(Event.OnItemClick(id)) },
                        onAddToListClick = { id -> event(Event.OnAddToListClick(id, state.type)) },
                        isAuthorized = state.isAuthorized
                    )
                }

                loadStateItem(
                    combinedLoadStates = searchDetails.loadState,
                    onRefresh = searchDetails::refresh,
                    onRetry = searchDetails::retry,
                    refreshLoadingContent = {
                        if (state.query.length < 3) Text(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .wrapContentSize(Alignment.Center),
                            text = stringResource(R.string.write_more_than_3_characters_for_starting_search)
                        ) else CircularProgressIndicator(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .wrapContentSize(Alignment.Center)
                        )
                    },
                    refreshNotLoadingContent = {
                        if (state.query.length < 3) Text(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .wrapContentSize(Alignment.Center),
                            text = stringResource(R.string.write_more_than_3_characters_for_starting_search)
                        ) else if (searchDetails.itemCount == 0) Text(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .wrapContentSize(Alignment.Center),
                            text = stringResource(R.string.response_is_empty)
                        )
                    }
                )
            }
        }
    }
}
