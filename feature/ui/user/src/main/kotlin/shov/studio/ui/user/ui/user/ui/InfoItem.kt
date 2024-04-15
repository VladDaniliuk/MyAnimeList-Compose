package shov.studio.ui.user.ui.user.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import core.ui.common.modifiers.placeholder

@Composable
fun InfoItem(
    itemText: String,
    isPlaceholderVisible: Boolean,
    numItem: Int,
    color: Color,
) {
    Row {
        Box(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .size(8.dp)
                .clip(MaterialTheme.shapes.extraSmall)
                .background(color)
        )

        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = itemText
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            modifier = Modifier.placeholder(enabled = isPlaceholderVisible),
            text = numItem.toString()
        )
    }
}

@Preview
@Composable
fun PieInfoItemPreview() {
    Surface {
        InfoItem(
            itemText = "Item",
            isPlaceholderVisible = false,
            numItem = 12,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
