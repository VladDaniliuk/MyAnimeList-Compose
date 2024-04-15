package core.ui.common.paging

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems

//TODO remove this method when standard lazy list state can save position of items
// link to issue : https://issuetracker.google.com/issues/177245496.
@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    // After recreation, LazyPagingItems first return 0 items, then the cached items.
    return when (itemCount) {
        // Return a different LazyListState instance.
        0 -> androidx.compose.foundation.lazy.rememberLazyListState(0, 0)
        // Return rememberLazyListState (normal case).
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}
