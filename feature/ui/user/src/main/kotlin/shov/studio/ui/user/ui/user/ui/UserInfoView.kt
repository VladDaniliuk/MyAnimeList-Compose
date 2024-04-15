package shov.studio.ui.user.ui.user.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import core.ui.piechart.PieChart
import core.ui.piechart.PieData
import shov.studio.ui.user.R
import shov.studio.ui.user.ui.user.UserContract
import shov.studio.ui.user.ui.user.UserContract.Event
import shov.studio.ui.user.ui.user.UserContract.State

@Composable
internal fun UserInfoView(state: State, event: (Event) -> Unit) {
    Box {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            UserImageView(
                picture = state.user.picture,
                name = state.user.name,
                isPlaceholderVisible = state.isPlaceholderVisible
            )

            UserAdditionalInfoView(state = state)

            PieChart(
                pieState = state.pieStatus,
                pieData = persistentListOf(
                    PieData(
                        MaterialTheme.colorScheme.onBackground,
                        state.user.animeStatistics.numItemsCompleted.toFloat()
                    ),
                    PieData(
                        MaterialTheme.colorScheme.primary,
                        state.user.animeStatistics.numItemsDropped.toFloat()
                    ),
                    PieData(
                        MaterialTheme.colorScheme.inversePrimary,
                        state.user.animeStatistics.numItemsOnHold.toFloat()
                    ),
                    PieData(
                        MaterialTheme.colorScheme.secondary,
                        state.user.animeStatistics.numItemsWatching.toFloat()
                    ),
                    PieData(
                        MaterialTheme.colorScheme.tertiary,
                        state.user.animeStatistics.numItemsPlanToWatch.toFloat()
                    ),
                )
            )

            InfoCard(state = state)
        }

        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .statusBarsPadding()
                .padding(end = 4.dp, top = 4.dp)
        ) {
            IconButton(onClick = { event(Event.OnMenuOpenClick) }) {
                Icon(
                    imageVector = Icons.Rounded.MoreVert,
                    contentDescription = Icons.Rounded.MoreVert.name
                )
            }

            DropdownMenu(
                expanded = state.isMenuOpened,
                onDismissRequest = { event(Event.OnMenuCloseClick) }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.log_out),
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    onClick = { event(Event.OnLogOutClick) }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserInfoPreview() {
    UserInfoView(state = State(status = UserContract.Status.COMPLETE)) {}
}
