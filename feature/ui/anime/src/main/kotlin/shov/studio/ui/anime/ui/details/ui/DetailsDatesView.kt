package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsDatesView(startDate: String?, endDate: String?, isLoading: Boolean) {
    if ((startDate != null) or (endDate != null)) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .padding(bottom = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (startDate != null) Card(
                modifier = Modifier
                    .weight(1f)
                    .width(IntrinsicSize.Max)
            ) {
                Column(modifier = Modifier.padding(all = 8.dp)) {
                    Text(
                        text = stringResource(R.string.start_date),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                enabled = isLoading,
                                backgroundColor = CardDefaults.cardColors().containerColor
                            ),
                        text = startDate
                    )
                }
            }

            if (endDate != null) Card(
                modifier = Modifier
                    .weight(1f)
                    .width(IntrinsicSize.Max)
            ) {
                Column(modifier = Modifier.padding(all = 8.dp)) {
                    Text(
                        text = stringResource(R.string.end_date),
                        style = MaterialTheme.typography.titleLarge
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .placeholder(
                                enabled = isLoading,
                                backgroundColor = CardDefaults.cardColors().containerColor
                            ),
                        text = endDate
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DetailsDatesPreview() {
    DetailsDatesView(startDate = "Apr 5, 2000", endDate = "Apr 5, 2000", isLoading = false)
}
