package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.LiveTv
import androidx.compose.material.icons.rounded.People
import androidx.compose.material.icons.rounded.PermIdentity
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.details.DetailsContract
import core.ui.preview.PreviewScreenSizes
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsInfoView(onNameClick: () -> Unit, detailsState: DetailsContract.State) {
    Column {
        Row(
            modifier = Modifier.padding(
                top = 8.dp,
                end = 8.dp,
                bottom = 8.dp
            )
        ) {
            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = onNameClick
            ) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = Icons.Outlined.Info.name
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .placeholder(enabled = detailsState.isLoading)
            ) {
                Text(
                    text = detailsState.details.title,
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = detailsState.details.jaTitle,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        InfoItem(
            text = stringResource(
                R.string.studios_list,
                stringResource(detailsState.details.mediaType.id),
                stringResource(detailsState.details.status.id)
            ),
            isPlaceholderVisible = detailsState.isLoading,
            imageVector = Icons.Rounded.CalendarMonth
        )

        InfoItem(
            text = detailsState.startDate(),
            isPlaceholderVisible = detailsState.isLoading,
            imageVector = Icons.Rounded.CalendarToday,
            isVisible = detailsState.startDate().isNotEmpty()
        )

        InfoItem(
            text = detailsState.episodes(),
            isPlaceholderVisible = detailsState.isLoading,
            imageVector = Icons.Rounded.LiveTv,
            isVisible = detailsState.episodes().isNotEmpty()
        )

        InfoItem(
            text = detailsState.studios(),
            isPlaceholderVisible = detailsState.isLoading,
            imageVector = Icons.Rounded.People,
            isVisible = detailsState.studios().isNotEmpty()
        )

        InfoItem(
            text = detailsState.authors(),
            isPlaceholderVisible = detailsState.isLoading,
            imageVector = Icons.Rounded.PermIdentity,
            isVisible = detailsState.authors().isNotEmpty()
        )
    }
}

@PreviewScreenSizes
@Composable
fun DetailsInfoPreview() {
    Surface {
        DetailsInfoView(onNameClick = {}, detailsState = DetailsContract.State("anime"))
    }
}
