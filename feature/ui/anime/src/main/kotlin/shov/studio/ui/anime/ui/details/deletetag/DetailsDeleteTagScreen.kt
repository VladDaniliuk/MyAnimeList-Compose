package shov.studio.ui.anime.ui.details.deletetag

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import shov.studio.ui.anime.R
import core.ui.preview.PreviewScreenSizes

@Composable
internal fun DetailsDeleteTagScreen(
    state: DetailsDeleteTagContract.State,
    event: (DetailsDeleteTagContract.Event) -> Unit,
    onDelete: () -> Unit,
) {
    Dialog(onDismissRequest = {
        if (state.isButtonActive) event(DetailsDeleteTagContract.Event.OnCancelClick)
    }) {
        Card(shape = MaterialTheme.shapes.extraLarge) {
            Column(
                modifier = Modifier
                    .widthIn(min = 280.dp, max = 560.dp)
                    .padding(all = 24.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.deleting_tag),
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = stringResource(R.string.delete_tag, state.tag),
                    style = MaterialTheme.typography.bodyMedium
                )

                val topPadding by animateDpAsState(
                    targetValue = if (state.isError) 8.dp else 0.dp,
                    label = "Top padding for error"
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp, top = topPadding),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onError
                    )
                ) {
                    AnimatedVisibility(visible = state.isError) {
                        Row(
                            modifier = Modifier
                                .padding(all = 24.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ErrorOutline,
                                contentDescription = null
                            )

                            Text(text = state.error ?: "")
                        }
                    }
                }

                Row(
                    modifier = Modifier.align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = { event(DetailsDeleteTagContract.Event.OnCancelClick) },
                        enabled = state.isButtonActive
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
                            onClick = onDelete,
                            enabled = state.isButtonActive
                        ) {
                            Text(text = stringResource(R.string.remove))
                        }
                    }
                }
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun DetailsDeleteTagPreview() {
    DetailsDeleteTagScreen(state = DetailsDeleteTagContract.State("TAG"), {}) {}
}
