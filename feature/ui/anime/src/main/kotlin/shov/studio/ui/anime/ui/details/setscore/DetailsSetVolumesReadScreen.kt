package shov.studio.ui.anime.ui.details.setscore

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import shov.studio.ui.anime.R

@Composable
internal fun DetailsSetVolumesReadScreen(state: DetailsSetChaptersReadContract.State, event: (DetailsSetChaptersReadContract.Event) -> Unit) {
    Dialog(onDismissRequest = { event(DetailsSetChaptersReadContract.Event.OnCancelClick) }) {
        Card {
            Column(
                modifier = Modifier
                    .widthIn(min = 280.dp, max = 560.dp)
                    .padding(vertical = 24.dp)
            ) {
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .padding(horizontal = 24.dp),
                    text = stringResource(state.headerId),
                    style = MaterialTheme.typography.headlineSmall
                )


                OutlinedTextField(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    value = state.value,
                    suffix = { Text(text = "/${state.max}") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { event(DetailsSetChaptersReadContract.Event.OnValueChange(it)) },
                    isError = state.isError,
                    supportingText = state.error?.let { text -> { Text(text) } }
                )

                Row(
                    modifier = Modifier
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        enabled = state.isLoading.not(),
                        onClick = { event(DetailsSetChaptersReadContract.Event.OnCancelClick) }
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }

                    AnimatedContent(
                        targetState = state.isLoading,
                        label = "Add tag button animation"
                    ) { isLoading ->
                        if (isLoading) CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) else TextButton(
                            enabled = state.canSave,
                            onClick = { event(DetailsSetChaptersReadContract.Event.OnSaveClick) }
                        ) {
                            Text(text = stringResource(R.string.save))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsSetVolumesReadPreview() {
    DetailsSetVolumesReadScreen(state = DetailsSetChaptersReadContract.State(
        R.string.volumes_read,
        500
    )
    ) {}
}
