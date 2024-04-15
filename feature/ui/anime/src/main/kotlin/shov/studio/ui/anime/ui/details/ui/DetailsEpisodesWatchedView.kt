package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.details.DetailsContract

@Composable
internal fun DetailsEpisodesWatchedView(
    detailsState: DetailsContract.State,
    event: (DetailsContract.Event) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        if (detailsState.isAnime) Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.watched_episodes)
            )

            EpisodesWatchedCard(
                detailsState.details.listStatus.numEpisodesWatched?.toString() ?: "0"
            ) {
                event(DetailsContract.Event.OnEpisodesWatchedClick)
            }
        } else {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(R.string.chapters_read)
                )

                EpisodesWatchedCard(
                    detailsState.details.listStatus.numChaptersRead?.toString() ?: "0"
                ) {
                    event(DetailsContract.Event.OnChaptersReadClick)
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    style = MaterialTheme.typography.titleLarge,
                    text = stringResource(R.string.volumes_read)
                )

                EpisodesWatchedCard(
                    detailsState.details.listStatus.numVolumesRead?.toString() ?: "0"
                ) {
                    event(DetailsContract.Event.OnVolumesReadClick)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsEpisodesWatchedPreview() {
    DetailsEpisodesWatchedView(detailsState = DetailsContract.State("anime"), event = {})
}
