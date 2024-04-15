package shov.studio.ui.anime.ui.details.addtolist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun StatusItemView(
    text: String,
    isSelected: Boolean,
    supportedText: String? = null,
    enabled: Boolean = true,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = enabled, onClick = onSelect),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            selected = isSelected,
            onClick = { onSelect() }
        )
        Column {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge
            )

            supportedText?.let {
                Text(text = supportedText, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
private fun StatusItemPreview() {
    Surface {
        StatusItemView(text = "Text", isSelected = true) {}
    }
}
