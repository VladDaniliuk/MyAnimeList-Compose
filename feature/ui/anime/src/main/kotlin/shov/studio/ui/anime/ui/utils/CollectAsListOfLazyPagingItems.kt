package shov.studio.ui.anime.ui.utils

import androidx.compose.runtime.Composable
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow

@Composable
internal fun <T : Any> ImmutableList<Flow<PagingData<T>>>.collectAsListOfLazyPagingItems() = map {
    it.collectAsLazyPagingItems()
}.toImmutableList()
