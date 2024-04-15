package shov.studio.ui.anime.ui.filter.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun <T> FilterExposedDropdownMenuBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    value: String,
    onSelected: (T) -> Unit,
    displayedText: String,
    entries: ImmutableList<Pair<String, T>>,
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange
    ) {
        OutlinedTextField(
            readOnly = true,
            modifier = modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = value,
            onValueChange = {},
            label = { Text(text = displayedText) },
            placeholder = { Text(text = displayedText) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) }
        ) {
            entries.forEach { entry ->
                DropdownMenuItem(
                    text = { Text(text = entry.first) },
                    onClick = { onSelected(entry.second) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun FilterExposedDropdownMenuBoxPreview() {
    FilterExposedDropdownMenuBox(
        expanded = true,
        onExpandedChange = {},
        value = "Text",
        onSelected = {},
        displayedText = "Displayed text",
        entries = persistentListOf("Text" to "Text", "Text" to "Text", "Text" to "Text")
    )
}
