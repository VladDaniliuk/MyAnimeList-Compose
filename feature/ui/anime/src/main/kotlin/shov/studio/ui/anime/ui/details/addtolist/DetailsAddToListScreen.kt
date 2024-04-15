package shov.studio.ui.anime.ui.details.addtolist

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import shov.studio.enums.Status
import shov.studio.ui.anime.R

@Composable
internal fun DetailsAddToListScreen(
    event: (DetailsAddToListContract.Event) -> Unit,
    state: DetailsAddToListContract.State,
) {
    Dialog(onDismissRequest = { event.invoke(DetailsAddToListContract.Event.OnCloseClick) }) {
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
                    text = stringResource(id = R.string.add_to_list),
                    style = MaterialTheme.typography.headlineSmall
                )

                if (state.isAnime) AnimeStatusListView(state = state, event = event)
                else MangaStatusListView(state = state, event = event)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
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
                        .padding(top = 24.dp, start = 24.dp, end = 24.dp)
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextButton(
                        onClick = { event.invoke(DetailsAddToListContract.Event.OnCloseClick) },
                        enabled = state.isLoading.not()
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
                            onClick = { event.invoke(DetailsAddToListContract.Event.OnSetClick) }
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
private fun DetailsAddToListPreview(
    @PreviewParameter(DetailsAddToListPreviewParameterProvider::class, limit = 2) type: String,
) {
    DetailsAddToListScreen(
        event = {},
        state = DetailsAddToListContract.State(Status.not_watching, type)
    )
}
