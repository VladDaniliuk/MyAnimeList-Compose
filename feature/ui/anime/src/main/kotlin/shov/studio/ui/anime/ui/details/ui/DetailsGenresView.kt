package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsGenresView(genres: ImmutableList<String>, isLoading: Boolean) {
    if (genres.isNotEmpty() or isLoading) {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isLoading) {
                items(count = 10) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 2.dp)
                            .size(width = 64.dp, height = 32.dp)
                            .clip(AssistChipDefaults.shape)
                            .placeholder(enabled = true)
                    )
                }
            }

            items(genres) { genre ->
                ElevatedAssistChip(
                    onClick = {},
                    label = { Text(text = genre) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun DetailsGenresPreview() {
    DetailsGenresView(genres = persistentListOf("1", "2", "3", "4"), isLoading = false)
}
