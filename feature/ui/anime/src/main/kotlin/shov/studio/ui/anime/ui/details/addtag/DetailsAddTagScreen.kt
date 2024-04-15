package shov.studio.ui.anime.ui.details.addtag

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import shov.studio.ui.anime.R

@Composable
internal fun DetailsAddTagScreen(
    state: DetailsAddTagContract.State,
    event: (DetailsAddTagContract.Event) -> Unit,
) {
    Dialog(
        onDismissRequest = {
            if (state.isCancelEnabled) event(DetailsAddTagContract.Event.OnBackClick)
        }
    ) {
        Card(shape = MaterialTheme.shapes.extraLarge) {
            Column(modifier = Modifier.padding(all = 24.dp)) {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(id = R.string.add_tag),
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    modifier = Modifier.padding(bottom = 12.dp),
                    value = state.tag,
                    onValueChange = { tag -> event(DetailsAddTagContract.Event.OnTagChange(tag)) },
                    readOnly = state.isReadOnly,
                    label = { Text(text = stringResource(R.string.tag)) },
                    placeholder = { Text(text = stringResource(R.string.tag)) },
                    isError = state.isError,
                    supportingText = state.error?.let { text -> { Text(text) } }
                )

                Row(
                    modifier = Modifier.padding(bottom = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(imageVector = Icons.Outlined.Info, contentDescription = "Info")

                    Text(
                        text = stringResource(R.string.add_tag_info),
                        color = MaterialTheme.colorScheme.secondary
                    )
                }

                Row(modifier = Modifier.align(Alignment.End)) {
                    TextButton(
                        onClick = { event(DetailsAddTagContract.Event.OnBackClick) },
                        enabled = state.isCancelEnabled
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }

                    AnimatedContent(
                        targetState = state.isLoading,
                        label = "Add tag button animation"
                    ) { isClicked ->
                        if (isClicked) CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) else TextButton(
                            onClick = { event(DetailsAddTagContract.Event.OnAddClick) },
                            enabled = state.isAddActive
                        ) {
                            Text(text = stringResource(R.string.add))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsAddTagPreview() {
    DetailsAddTagScreen(state = DetailsAddTagContract.State()) {}
}
