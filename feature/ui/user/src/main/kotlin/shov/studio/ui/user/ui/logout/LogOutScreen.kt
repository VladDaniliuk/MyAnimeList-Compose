package shov.studio.ui.user.ui.logout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import shov.studio.feature.ui.models.data.models.UserModel
import shov.studio.ui.user.R
import shov.studio.ui.user.ui.logout.LogOutContract.Event

@Composable
internal fun LogOutScreen(state: LogOutContract.State, event: (Event) -> Unit) {
    Surface(
        modifier = Modifier.widthIn(min = 280.dp, max = 560.dp),
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surfaceContainerHigh,
    ) {
        Column(modifier = Modifier.padding(all = 24.dp)) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(id = R.string.log_out),
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(R.string.are_you_sure_you_want_log_out_from_this_account),
                style = MaterialTheme.typography.bodyMedium
            )

            ListItem(
                modifier = Modifier.padding(bottom = 24.dp),
                leadingContent = {
                    AsyncImage(
                        model = state.user.picture,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(48.dp)
                    )
                },
                headlineContent = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.user.name
                    )
                },
                supportingContent = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = state.user.id
                    )
                },
                colors = ListItemDefaults
                    .colors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
            )

            Row(modifier = Modifier.align(Alignment.End)) {
                TextButton(onClick = { event(Event.OnCancelClick) }) {
                    Text(text = stringResource(R.string.cancel))
                }

                TextButton(onClick = { event(Event.OnLogOutClick) }) {
                    Text(
                        color = MaterialTheme.colorScheme.error,
                        text = stringResource(id = R.string.log_out)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun LogOutPreview() {
    LogOutScreen(state = LogOutContract.State(UserModel("0", "NAME", null))) {}
}
