package shov.studio.ui.user.ui.user.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.user.R
import shov.studio.ui.user.ui.user.UserContract
import shov.studio.ui.user.ui.user.UserContract.State

@Composable
internal fun InfoCard(state: State) {
    Card(modifier = Modifier.padding(12.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 32.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            InfoItem(
                itemText = stringResource(R.string.completed),
                isPlaceholderVisible = state.isPlaceholderVisible,
                numItem = state.user.animeStatistics.numItemsCompleted,
                color = MaterialTheme.colorScheme.onBackground
            )

            InfoItem(
                itemText = stringResource(R.string.dropped),
                isPlaceholderVisible = state.isPlaceholderVisible,
                numItem = state.user.animeStatistics.numItemsDropped,
                color = MaterialTheme.colorScheme.primary
            )

            InfoItem(
                itemText = stringResource(R.string.on_hold),
                isPlaceholderVisible = state.isPlaceholderVisible,
                numItem = state.user.animeStatistics.numItemsOnHold,
                color = MaterialTheme.colorScheme.inversePrimary
            )

            InfoItem(
                itemText = stringResource(R.string.watching),
                isPlaceholderVisible = state.isPlaceholderVisible,
                numItem = state.user.animeStatistics.numItemsWatching,
                color = MaterialTheme.colorScheme.secondary
            )

            InfoItem(
                itemText = stringResource(R.string.plan_to_watch),
                isPlaceholderVisible = state.isPlaceholderVisible,
                numItem = state.user.animeStatistics.numItemsPlanToWatch,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Preview
@Composable
private fun InfoCardPreview() {
    InfoCard(state = State(status = UserContract.Status.COMPLETE))
}
