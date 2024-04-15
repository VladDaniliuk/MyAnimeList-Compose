package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.data.models.StatusModel
import shov.studio.ui.anime.ui.utils.toNonZeroFloat
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsStatisticView(status: StatusModel?, isLoading: Boolean) {
    status?.let {
        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = stringResource(R.string.statistic),
            style = MaterialTheme.typography.titleLarge
        )

        Row(
            modifier = Modifier
                .padding(start = 6.dp, top = 4.dp, end = 6.dp)
                .clip(MaterialTheme.shapes.small)
                .placeholder(enabled = isLoading),
        ) {
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .weight(status.watching.toNonZeroFloat())
                    .background(MaterialTheme.colorScheme.primary)
            )

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .weight(status.completed.toNonZeroFloat())
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .weight(status.onHold.toNonZeroFloat())
                    .background(MaterialTheme.colorScheme.tertiary)
            )

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .weight(status.dropped.toNonZeroFloat())
                    .background(MaterialTheme.colorScheme.tertiaryContainer)
            )

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .weight(status.planToWatch.toNonZeroFloat())
                    .background(MaterialTheme.colorScheme.onSurfaceVariant)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                StatusInfo(
                    isPlaceholderVisible = isLoading,
                    status = stringResource(R.string.watching),
                    value = status.watching,
                    color = MaterialTheme.colorScheme.primary
                )

                StatusInfo(
                    isPlaceholderVisible = isLoading,
                    status = stringResource(R.string.completed),
                    value = status.completed,
                    color = MaterialTheme.colorScheme.primaryContainer
                )

                StatusInfo(
                    isPlaceholderVisible = isLoading,
                    status = stringResource(R.string.on_hold),
                    value = status.onHold,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                StatusInfo(
                    isPlaceholderVisible = isLoading,
                    status = stringResource(R.string.dropped),
                    value = status.dropped,
                    color = MaterialTheme.colorScheme.tertiaryContainer
                )

                StatusInfo(
                    isPlaceholderVisible = isLoading,
                    status = stringResource(R.string.plan_to_watch),
                    value = status.planToWatch,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsStatisticPreview() {
    Surface {
        Column {
            DetailsStatisticView(status = StatusModel(12, 15, 18, 24, 16), isLoading = false)
        }
    }
}