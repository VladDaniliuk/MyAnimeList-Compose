package shov.studio.ui.user.ui.user.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.ui.common.modifiers.placeholder
import shov.studio.ui.user.R
import shov.studio.ui.user.ui.user.UserContract
import shov.studio.ui.user.ui.user.UserContract.State

@Composable
internal fun UserAdditionalInfoView(state: State) {
    Column {
        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .placeholder(enabled = state.isPlaceholderVisible),
            text = stringResource(R.string.id, state.user.id)
        )

        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .placeholder(enabled = state.isPlaceholderVisible),
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(R.string.days, state.user.animeStatistics.numDays)
        )

        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .placeholder(enabled = state.isPlaceholderVisible),
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(R.string.mean_score, state.user.animeStatistics.meanScore)
        )

        Text(
            modifier = Modifier
                .padding(start = 12.dp)
                .placeholder(enabled = state.isPlaceholderVisible),
            style = MaterialTheme.typography.titleMedium,
            text = stringResource(R.string.episodes, state.user.animeStatistics.numEpisodes)
        )

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = stringResource(R.string.joined_at))

                    Text(
                        modifier = Modifier.placeholder(enabled = state.isPlaceholderVisible),
                        style = MaterialTheme.typography.titleMedium,
                        text = state.user.joinedAt
                    )
                }
            }

            if (state.user.location.isNotEmpty()) Card {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(text = stringResource(R.string.location))

                    Text(
                        modifier = Modifier.placeholder(enabled = state.isPlaceholderVisible),
                        style = MaterialTheme.typography.titleMedium,
                        text = state.user.location
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserAdditionalInfoPreview() {
    UserAdditionalInfoView(state = State(status = UserContract.Status.COMPLETE))
}
