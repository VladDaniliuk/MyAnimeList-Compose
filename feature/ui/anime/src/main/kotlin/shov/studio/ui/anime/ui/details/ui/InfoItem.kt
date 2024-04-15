package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.ui.common.modifiers.placeholder

@Composable
internal fun InfoItem(
    text: String,
    isPlaceholderVisible: Boolean,
    imageVector: ImageVector,
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
) {
    if (isVisible or isPlaceholderVisible) Row(
        modifier = modifier.padding(start = 12.dp, bottom = 4.dp, end = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = imageVector.name
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .placeholder(enabled = isPlaceholderVisible),
            text = text
        )
    }
}

@Preview
@Composable
private fun InfoItemPreview() {
    InfoItem(
        text = "TV, ended",
        isPlaceholderVisible = false,
        imageVector = Icons.Rounded.CalendarMonth
    )
}
