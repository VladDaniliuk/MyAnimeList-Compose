package shov.studio.ui.anime.ui.details.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.details.DetailsContract
import core.ui.common.text.AnimatedText

@Composable
internal fun DetailsUserView(
    detailsState: DetailsContract.State,
    event: (DetailsContract.Event) -> Unit,
) {
    Column(modifier = Modifier.animateContentSize()) {
        if (detailsState.isAuthorized) {
            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.tags)
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                LazyRow(
                    modifier = Modifier.weight(1f),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(detailsState.details.listStatus.tags) { tag ->
                        InputChip(
                            selected = true,
                            onClick = { event(DetailsContract.Event.OnTagDeleteClick(tag)) },
                            label = { Text(text = tag) },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Rounded.Close,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }

                IconButton(onClick = { event(DetailsContract.Event.OnTagAddClick) }) {
                    Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                }
            }

            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                onClick = { event(DetailsContract.Event.OnAddToListClick) }
            ) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = Icons.Rounded.Add.name)

                AnimatedText(
                    text = stringResource(detailsState.details.listStatus.status.id)
                )
            }

            DetailsScoreView(detailsState, event)

            DetailsEpisodesWatchedView(detailsState = detailsState, event = event)

            Text(
                modifier = Modifier.padding(start = 12.dp, top = 12.dp, bottom = 12.dp),
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.comment)
            )

            DetailsCommentView(detailsState = detailsState, event = event)
        }
    }
}

@Preview
@Composable
fun DetailsUserPreview() {
    Surface {
        DetailsUserView(
            detailsState = DetailsContract.State("anime", isAuthorized = true),
            event = {}
        )
    }
}
