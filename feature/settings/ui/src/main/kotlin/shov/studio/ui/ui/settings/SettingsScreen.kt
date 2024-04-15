package shov.studio.ui.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.settings.ui.R
import core.ui.common.text.AnimatedText
import shov.studio.ui.ui.settings.SettingsContract.Event
import shov.studio.ui.ui.settings.SettingsContract.State
import shov.studio.ui.ui.settings.SettingsContract.Status
import shov.studio.ui.ui.settings.ui.DropDownListItem
import shov.studio.ui.ui.settings.ui.SettingsAuthErrorView
import shov.studio.ui.ui.settings.ui.SettingsNeedAuthView

@Composable
internal fun SettingsScreen(
    state: State,
    event: (Event) -> Unit,
) {
    Surface(
        shape = MaterialTheme.shapes.extraLarge,
        color = MaterialTheme.colorScheme.surfaceContainerHigh
    ) {
        Column(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { event(Event.OnBackClick) }
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Close,
                        contentDescription = Icons.Rounded.Close.name
                    )
                }

                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.headlineSmall
                )
            }

            when (state.status) {
                Status.LOADING, Status.COMPLETE -> SettingsUserView(event = event, state = state)
                Status.NEED_AUTH -> SettingsNeedAuthView { event(Event.OnLogInClick) }
                Status.AUTH_ERROR -> SettingsAuthErrorView(state.error) {
                    event(Event.OnLogInClick)
                }
            }

            DropDownListItem(
                currentTheme = state.theme,
                isExpanded = state.isThemeExpanded,
                onExpandClick = { isExpanded -> event(Event.OnThemExpandClick(isExpanded)) },
                onItemClick = { theme -> event(Event.OnThemeClick(theme)) }
            )

            ListItem(
                headlineContent = { Text(text = stringResource(R.string.dynamic_colors)) },
                supportingContent = {
                    AnimatedText(
                        text = stringResource(
                            if (state.isDynamicColor) R.string.enabled else R.string.disabled
                        )
                    )
                },
                trailingContent = {
                    Switch(
                        checked = state.isDynamicColor,
                        onCheckedChange = { isDynamicColor ->
                            event(Event.OnDynamicColorChange(isDynamicColor))
                        }
                    )
                },
                colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh)

            )

            HorizontalDivider()

            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = { event(Event.OnUriClick) }
            ) {
                Text(text = stringResource(R.string.privacy_policy))
            }
        }
    }
}

@Preview
@Composable
private fun SettingsPreview() {
    SettingsScreen(state = State()) {}
}
