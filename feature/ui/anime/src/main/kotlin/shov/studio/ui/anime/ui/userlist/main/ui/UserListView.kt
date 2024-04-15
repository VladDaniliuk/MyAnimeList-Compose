package shov.studio.ui.anime.ui.userlist.main.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.userlist.main.UserListContract
import shov.studio.ui.anime.ui.userlist.main.UserListContract.SubState

@Composable
internal fun UserListView(
    type: String,
    state: SubState,
    event: (UserListContract.Event) -> Unit,
) {
    Column {
        when (state.status) {
            UserListContract.Status.LOADING, UserListContract.Status.COMPLETE -> if (
                state.details.isNotEmpty() or
                (state.status == UserListContract.Status.LOADING)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(
                            if (type == AnimeRoute.Type.anime) R.string.watched_animes
                            else R.string.read_mangas
                        ),
                        style = MaterialTheme.typography.titleLarge
                    )

                    TextButton(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = { event(UserListContract.Event.OnListClick(type)) }
                    ) {
                        Text(text = stringResource(R.string.show_all))
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(state.details) { details ->
                        ItemImageView(event = event, type = type, details = details)
                    }
                }
            } else {
                ListItem(
                    modifier = Modifier.clickable {
                        event(UserListContract.Event.OnListClick(type))
                    },
                    headlineContent = {
                        Text(
                            text = stringResource(
                                if (type == AnimeRoute.Type.anime) R.string.show_my_anime_lists
                                else R.string.show_my_manga_lists
                            )
                        )
                    })
            }

            UserListContract.Status.ERROR -> UserListErrorView(state, event)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserListPreview() {
    UserListView(type = AnimeRoute.Type.anime, state = SubState()) {}
}
