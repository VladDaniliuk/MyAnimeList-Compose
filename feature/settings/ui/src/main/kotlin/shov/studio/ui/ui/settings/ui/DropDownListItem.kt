package shov.studio.ui.ui.settings.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.core.data.Theme
import shov.studio.settings.ui.R
import core.ui.common.text.AnimatedText

@Composable
fun DropDownListItem(
    currentTheme: Theme,
    isExpanded: Boolean,
    onExpandClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onItemClick: (Theme) -> Unit,
) {
    val degrees by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "ThemeRotation"
    )

    ListItem(
        modifier = modifier.clickable(onClick = { onExpandClick(true) }),
        headlineContent = { Text(text = stringResource(R.string.theme)) },
        supportingContent = { AnimatedText(text = stringResource(currentTheme.id)) },
        trailingContent = {
            Box {
                IconButton(onClick = { onExpandClick(true) }) {
                    Icon(
                        modifier = Modifier.rotate(degrees),
                        imageVector = Icons.Rounded.ArrowDropDown,
                        contentDescription = Icons.Rounded.ArrowDropDown.name
                    )
                }

                DropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { onExpandClick(false) }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(Theme.DEFAULT.id)) },
                        onClick = { onItemClick(Theme.DEFAULT) }
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(Theme.DARK.id)) },
                        onClick = { onItemClick(Theme.DARK) }
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(Theme.LIGHT.id)) },
                        onClick = { onItemClick(Theme.LIGHT) }
                    )
                }
            }
        },
        colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)
    )
}

@Preview
@Composable
fun DropDownListItemPreview() {
    DropDownListItem(
        onExpandClick = {},
        currentTheme = Theme.DEFAULT,
        isExpanded = false
    ) {}
}
