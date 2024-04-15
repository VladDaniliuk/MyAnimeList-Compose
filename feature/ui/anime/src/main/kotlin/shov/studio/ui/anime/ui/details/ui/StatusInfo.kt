package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.ui.common.modifiers.placeholder

@Composable
internal fun StatusInfo(
    isPlaceholderVisible: Boolean,
    status: String,
    value: Int,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(8.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(color)
        )

        Text(
            text = status,
            overflow = TextOverflow.Ellipsis,
            softWrap = false,
            maxLines = 1,
        )

        Text(
            modifier = Modifier
                .weight(weight = 1f)
                .placeholder(enabled = isPlaceholderVisible),
            text = value.toString(),
        )
    }
}

@Preview
@Composable
private fun StatusInfoPreview() {
    StatusInfo(
        modifier = Modifier.width(100.dp),
        isPlaceholderVisible = false,
        status = "Status",
        value = 123154415,
        color = MaterialTheme.colorScheme.primary
    )
}
