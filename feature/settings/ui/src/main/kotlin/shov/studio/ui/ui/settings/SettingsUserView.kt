package shov.studio.ui.ui.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowDropDownCircle
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.ManageAccounts
import androidx.compose.material.icons.rounded.PersonAddAlt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.size.Size
import core.ui.common.modifiers.placeholder
import core.ui.common.text.AnimatedText
import kotlinx.collections.immutable.persistentListOf
import shov.studio.feature.ui.models.data.models.UserModel
import shov.studio.ui.ui.settings.SettingsContract.Event

@Composable
internal fun SettingsUserView(
    event: (Event) -> Unit,
    state: SettingsContract.State,
) {
    Card(modifier = Modifier.padding(horizontal = 8.dp)) {
        ListItem(
            modifier = Modifier.clickable { event(Event.OnUserListClick) },
            leadingContent = {
                val painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(state.user.picture)
                        .size(Size.ORIGINAL)
                        .build()
                )

                if ((state.user.picture?.isEmpty() != false) and state.isPlaceholder.not()) Icon(
                    modifier = Modifier
                        .size(48.dp),
                    imageVector = Icons.Rounded.Face,
                    contentDescription = state.user.name,
                ) else Image(
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(48.dp)
                        .placeholder(enabled = painter.state !is AsyncImagePainter.State.Success),
                    painter = painter,
                    contentDescription = state.user.name,
                    contentScale = ContentScale.Crop
                )
            },
            headlineContent = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 1.dp)
                        .placeholder(enabled = state.isPlaceholder),
                    text = state.user.name
                )
            },
            supportingContent = {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(enabled = state.isPlaceholder),
                    text = state.user.id
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.Outlined.ArrowDropDownCircle,
                    contentDescription = null,
                    modifier = Modifier.rotate(if (state.isListOpened) 180f else 0f)
                )
            }
        )

        AnimatedVisibility(visible = state.isListOpened) {
            Column {
                HorizontalDivider()

                state.otherAccounts.forEach { model ->
                    ListItem(
                        modifier = Modifier.clickable {
                            event(Event.OnUserChangeClick(model.id))
                        },
                        leadingContent = {
                            val painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(model.picture)
                                    .size(Size.ORIGINAL)
                                    .build()
                            )

                            if ((model.picture?.isEmpty() != false) and state.isPlaceholder.not()) Icon(
                                modifier = Modifier
                                    .size(48.dp),
                                imageVector = Icons.Rounded.Face,
                                contentDescription = model.name,
                            ) else Image(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(48.dp)
                                    .placeholder(enabled = painter.state !is AsyncImagePainter.State.Success),
                                painter = painter,
                                contentDescription = model.name,
                                contentScale = ContentScale.Crop
                            )
                        },
                        headlineContent = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 1.dp)
                                    .placeholder(enabled = state.isPlaceholder),
                                text = model.name
                            )
                        },
                        supportingContent = {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .placeholder(enabled = state.isPlaceholder),
                                text = model.id
                            )
                        }
                    )
                }

                HorizontalDivider()

                ListItem(
                    modifier = Modifier.clickable { event(Event.OnAddNewAccountClick) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Rounded.PersonAddAlt,
                            contentDescription = null
                        )
                    },
                    headlineContent = { AnimatedText(text = "Add account") }
                )

                ListItem(
                    modifier = Modifier.clickable { event(Event.OnAccountManagementClick) },
                    leadingContent = {
                        Icon(
                            imageVector = Icons.Rounded.ManageAccounts,
                            contentDescription = null
                        )
                    }, headlineContent = { Text(text = "Accounts management") }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsUserPreview() {
    SettingsUserView(
        event = {},
        state = SettingsContract.State(
            status = SettingsContract.Status.COMPLETE,
            user = UserModel("1234567890", "Long Long Name", ""),
            isListOpened = true,
            otherAccounts = persistentListOf(
                UserModel("1234567890", "Long Long Name", ""),
                UserModel("1234567890", "Long Long Name", ""),
                UserModel("1234567890", "Long Long Name", "")
            )
        )
    )
}
