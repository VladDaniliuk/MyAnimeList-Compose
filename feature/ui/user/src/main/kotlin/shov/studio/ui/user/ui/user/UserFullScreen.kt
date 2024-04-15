package shov.studio.ui.user.ui.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.ui.user.ui.user.UserContract.Event
import shov.studio.ui.user.ui.user.UserContract.State
import shov.studio.ui.user.ui.user.UserContract.Status
import shov.studio.ui.user.ui.user.ui.UserAuthErrorView
import shov.studio.ui.user.ui.user.ui.UserInfoError
import shov.studio.ui.user.ui.user.ui.UserInfoView
import shov.studio.ui.user.ui.user.ui.UserNeedAuthView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserFullScreen(
    state: State,
    pullToRefreshState: PullToRefreshState,
    event: (Event) -> Unit,
) {
    if (pullToRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            event(Event.OnRefresh)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        when (state.status) {
            Status.LOADING, Status.COMPLETE, Status.REFRESH ->
                UserInfoView(state = state, event = event)

            Status.ERROR -> UserInfoError(state = state, event = event)
            Status.NEED_AUTH -> UserNeedAuthView(event = event)
            Status.AUTH_ERROR -> UserAuthErrorView(state = state, event = event)
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun UserFullPreview() {
    UserFullScreen(
        state = State(status = Status.COMPLETE),
        pullToRefreshState = rememberPullToRefreshState()
    ) {}
}
