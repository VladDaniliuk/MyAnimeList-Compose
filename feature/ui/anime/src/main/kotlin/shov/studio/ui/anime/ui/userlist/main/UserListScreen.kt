package shov.studio.ui.anime.ui.userlist.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.userlist.main.UserListContract.Event
import shov.studio.ui.anime.ui.userlist.main.ui.UserListView
import core.ui.common.scaffold.CustomScaffold

@Composable
internal fun UserListScreen(
    state: UserListContract.State,
    event: (Event) -> Unit,
) {
    CustomScaffold(title = stringResource(R.string.user_list)) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            UserListView(type = AnimeRoute.Type.anime, state = state.anime, event = event)

            UserListView(type = AnimeRoute.Type.manga, state = state.manga, event = event)
        }
    }
}

@Preview
@Composable
private fun UserListPreview() {
    UserListScreen(state = UserListContract.State()) {}
}