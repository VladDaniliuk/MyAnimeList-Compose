package core.ui.common.loaderror

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

fun LazyListScope.loadStateItem(
    combinedLoadStates: CombinedLoadStates,
    onRefresh: () -> Unit,
    onRetry: () -> Unit,
    refreshLoadingContent: @Composable LazyItemScope.() -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier
                .fillParentMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    },
    refreshErrorContent: @Composable LazyItemScope.(LoadState.Error) -> Unit = { loadStateError ->
        LoadErrorView(
            modifier = Modifier.fillParentMaxHeight(),
            loadStateError = loadStateError,
            onRefresh = onRefresh
        )
    },
    refreshNotLoadingContent: @Composable LazyItemScope.() -> Unit = {},
    appendLoadingContent: @Composable LazyItemScope.() -> Unit = {
        CircularProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .wrapContentWidth(Alignment.CenterHorizontally)
        )
    },
    appendErrorContent: @Composable LazyItemScope.(LoadState.Error) -> Unit = { loadStateError ->
        LoadErrorView(
            loadStateError = loadStateError,
            paddingValues = PaddingValues(vertical = 16.dp),
            onRefresh = onRetry
        )
    },
    appendNotLoadingContent: @Composable LazyItemScope.() -> Unit = {},
) {
    when (val refresh = combinedLoadStates.refresh) {
        is LoadState.Loading -> item { refreshLoadingContent() }
        is LoadState.Error -> item { refreshErrorContent(refresh) }
        is LoadState.NotLoading -> item { refreshNotLoadingContent() }
    }

    when (val append = combinedLoadStates.append) {
        is LoadState.Loading -> item { appendLoadingContent() }
        is LoadState.Error -> item { appendErrorContent(append) }
        else -> item { appendNotLoadingContent() }
    }
}
