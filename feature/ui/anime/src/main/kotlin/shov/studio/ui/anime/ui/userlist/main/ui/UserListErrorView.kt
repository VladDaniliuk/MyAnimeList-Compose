package shov.studio.ui.anime.ui.userlist.main.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.userlist.main.UserListContract
import shov.studio.ui.anime.ui.userlist.main.UserListContract.Status
import shov.studio.ui.anime.ui.userlist.main.UserListContract.SubState

@Composable
internal fun UserListErrorView(
    state: SubState,
    event: (UserListContract.Event) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.titleMedium
            )

            Text(text = state.error!!,modifier = Modifier.padding(bottom = 4.dp))

            Button(onClick = { event(UserListContract.Event.OnLoadRetryClick) }) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}

@Preview
@Composable
private fun UserListErrorPreview() {
    UserListErrorView(
        state = SubState(
            details = persistentListOf(),
            status = Status.ERROR,
            error = "Some error"
        )
    ) {}
}
