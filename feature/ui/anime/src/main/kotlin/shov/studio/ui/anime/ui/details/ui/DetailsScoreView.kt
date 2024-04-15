package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
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
internal fun DetailsScoreView(
    detailsState: DetailsContract.State,
    event: (DetailsContract.Event) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = stringResource(R.string.rewatching)
            )

            Switch(
                checked = detailsState.details.listStatus.isRewatching,
                onCheckedChange = { isRewatching ->
                    event(DetailsContract.Event.OnRewathcingClick(isRewatching))
                }
            )
        }

        Card(modifier = Modifier.weight(2f)) {
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp, horizontal = 4.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.my_score),
                        style = MaterialTheme.typography.titleLarge
                    )

                    OutlinedButton(onClick = { event(DetailsContract.Event.OnSetScoreClick) }) {
                        Text(text = stringResource(R.string.change))
                    }
                }

                AnimatedText(
                    text = detailsState.details.listStatus.score.toString(),
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun DetailsScorePreview() {
    DetailsScoreView(detailsState = DetailsContract.State("anime"), event = {})
}
