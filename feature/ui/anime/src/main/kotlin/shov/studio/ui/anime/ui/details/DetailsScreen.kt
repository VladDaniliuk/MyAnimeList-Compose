package shov.studio.ui.anime.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.ui.details.ui.DetailsErrorView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DetailsScreen(
    detailsState: DetailsContract.State,
    pullToRefreshState: PullToRefreshState,
    event: (DetailsContract.Event) -> Unit,
) {
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            event(DetailsContract.Event.OnRefreshClick)
        }
    }

    Surface {
        Box(modifier = Modifier.nestedScroll(pullToRefreshState.nestedScrollConnection)) {
            when (detailsState.status) {
                DetailsContract.Status.EMPTY_ERROR -> DetailsErrorView(
                    error = detailsState.error?.error,
                    onErrorRetry = { event(DetailsContract.Event.OnErrorClick) },
                )

                else -> DetailsView(event = event, state = detailsState)
            }

            IconButton(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp)
                    .statusBarsPadding()
                    .padding(vertical = 8.dp),
                onClick = { event(DetailsContract.Event.OnBackClick) },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = Icons.AutoMirrored.Rounded.ArrowBack.name
                )
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
private fun DetailsPreview(
) {
    DetailsScreen(
        detailsState = DetailsContract.State(type = "anime", isAuthorized = true),
        pullToRefreshState = rememberPullToRefreshState()
    ) {}
}
