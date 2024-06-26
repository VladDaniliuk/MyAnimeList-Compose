package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import core.ui.common.text.AnimatedText

@Composable
internal fun EpisodesWatchedCard(
    number: String,
    onEdit: () -> Unit,
) {
    OutlinedCard {
        Row(
            modifier = Modifier.padding(all = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedText(
                modifier = Modifier.padding(start = 8.dp),
                text = number,
                style = MaterialTheme.typography.headlineLarge
            )

            IconButton(onClick = onEdit) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = stringResource(R.string.edit)
                )
            }
        }
    }
}

@Preview
@Composable
fun EpisodesWatchedCardPreview() {
    EpisodesWatchedCard(number = "10") {}
}
