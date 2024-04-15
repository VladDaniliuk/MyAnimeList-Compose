package shov.studio.ui.anime.ui.details.setscore

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import shov.studio.ui.anime.R
import core.ui.common.text.AnimatedText
import kotlin.math.roundToInt

@Composable
internal fun DetailsSetScoreScreen(
    state: DetailsSetScoreContract.State,
    event: (DetailsSetScoreContract.Event) -> Unit,
) {
    Dialog(onDismissRequest = { event.invoke(DetailsSetScoreContract.Event.OnBackClick) }) {
        Card(shape = MaterialTheme.shapes.extraLarge) {
            Column(
                modifier = Modifier
                    .widthIn(min = 280.dp, max = 560.dp)
                    .padding(all = 24.dp)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.set_score),
                    style = MaterialTheme.typography.headlineSmall
                )

                AnimatedText(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = state.score.roundToInt().toString(),
                    style = MaterialTheme.typography.titleLarge
                )

                Slider(
                    value = state.score,
                    enabled = state.isLoading.not(),
                    onValueChange = { score ->
                        event.invoke(DetailsSetScoreContract.Event.OnScoreChanged(score))
                    },
                    valueRange = 0f..10f,//10
                    steps = 9
                )

                Card(
                    modifier = Modifier.fillMaxWidth(),
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
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        enabled = state.isLoading.not(),
                        onClick = { event.invoke(DetailsSetScoreContract.Event.OnBackClick) }
                    ) {
                        Text(text = stringResource(R.string.cancel))
                    }

                    AnimatedContent(
                        targetState = state.isLoading,
                        label = "Add tag button animation"
                    ) { isLoading ->
                        if (isLoading) CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.CenterVertically)
                        ) else TextButton(
                            onClick = { event.invoke(DetailsSetScoreContract.Event.OnSetClick) }
                        ) {
                            Text(text = stringResource(R.string.set))
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsSetScorePreview() {
    var score by remember { mutableFloatStateOf(0f) }
    DetailsSetScoreScreen(state = DetailsSetScoreContract.State(score = score), event = {
        if (it is DetailsSetScoreContract.Event.OnScoreChanged) score = it.score
    })
}
