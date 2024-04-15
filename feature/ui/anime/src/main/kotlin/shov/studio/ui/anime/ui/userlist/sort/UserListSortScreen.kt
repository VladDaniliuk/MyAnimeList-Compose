package shov.studio.ui.anime.ui.userlist.sort

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.enums.Sort
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.details.addtolist.StatusItemView

@Composable
internal fun UserListSortScreen(
    state: UserListSortContract.State,
    event: (UserListSortContract.Event) -> Unit,
) {
    Surface(shape = MaterialTheme.shapes.extraLarge) {
        Column(
            modifier = Modifier
                .widthIn(min = 280.dp, max = 560.dp)
                .padding(vertical = 24.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .padding(horizontal = 24.dp),
                text = stringResource(R.string.choose_using_sort),
                style = MaterialTheme.typography.headlineSmall
            )

            StatusItemView(
                enabled = true,
                text = stringResource(R.string.list_score),
                isSelected = state.isSelected(Sort.list_score),
            ) {
                event(UserListSortContract.Event.OnSortClick(Sort.list_score))
            }

            StatusItemView(
                enabled = true,
                text = stringResource(R.string.list_updated_at),
                isSelected = state.isSelected(Sort.list_updated_at),
            ) {
                event(UserListSortContract.Event.OnSortClick(Sort.list_updated_at))
            }

            StatusItemView(
                enabled = true,
                text = stringResource(R.string.anime_title),
                isSelected = state.isSelected(Sort.anime_title, Sort.manga_title),
            ) {
                event(
                    UserListSortContract.Event.OnSortClick(
                        if (state.type == AnimeRoute.Type.anime) Sort.anime_title
                        else Sort.manga_title
                    )
                )
            }

            StatusItemView(
                enabled = true,
                text = stringResource(R.string.anime_start_date),
                isSelected = state.isSelected(Sort.anime_start_date, Sort.manga_start_date),
            ) {
                event(
                    UserListSortContract.Event.OnSortClick(
                        if (state.type == AnimeRoute.Type.anime) Sort.anime_start_date
                        else Sort.manga_start_date
                    )
                )
            }

            Row(
                modifier = Modifier
                    .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                    .align(Alignment.End),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = { event(UserListSortContract.Event.OnCancelClick) }) {
                    Text(text = stringResource(R.string.cancel))
                }

                TextButton(onClick = { event(UserListSortContract.Event.OnSaveClick) }) {
                    Text(text = stringResource(R.string.choose))
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserListSortPreview() {
    UserListSortScreen(state = UserListSortContract.State("anime", Sort.list_score)) {}
}
