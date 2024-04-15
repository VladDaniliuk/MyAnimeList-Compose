package shov.studio.ui.user.ui.accauntmanagement

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil3.compose.AsyncImage
import kotlinx.coroutines.flow.flowOf
import core.ui.common.scaffold.CustomScaffold
import shov.studio.feature.ui.models.data.models.UserModel
import shov.studio.ui.user.R
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract.Event
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract.State

@Composable
internal fun AccountManagementScreen(
    state: State,
    event: (Event) -> Unit,
) {
    val accounts = state.list.collectAsLazyPagingItems()

    CustomScaffold(
        title = stringResource(R.string.account_management),
        onBackClick = { event(Event.OnBackClick) }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = paddingValues) {
            items(
                count = accounts.itemCount,
                key = accounts.itemKey(key = UserModel::id),
                contentType = accounts.itemContentType()
            ) { index ->
                accounts[index]?.let { account ->
                    ListItem(
                        leadingContent = {
                            AsyncImage(
                                model = account.picture,
                                contentDescription = null,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .size(48.dp)
                            )
                        },
                        headlineContent = {
                            Text(modifier = Modifier.fillMaxWidth(), text = account.name)
                        },
                        supportingContent = {
                            Text(modifier = Modifier.fillMaxWidth(), text = account.id)
                        },
                        trailingContent = {
                            val (isMenuOpen, onMenuOpened) = remember { mutableStateOf(false) }

                            IconButton(onClick = { onMenuOpened(isMenuOpen.not()) }) {
                                Icon(
                                    imageVector = Icons.Rounded.MoreVert,
                                    contentDescription = null
                                )
                            }

                            DropdownMenu(
                                expanded = isMenuOpen,
                                onDismissRequest = { onMenuOpened(false) }
                            ) {
                                if (index != 0) DropdownMenuItem(
                                    text = { Text(text = stringResource(R.string.use_as_default)) },
                                    onClick = {
                                        onMenuOpened(false)

                                        event(Event.OnDefaultClick(account.id))
                                    }
                                )

                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = stringResource(R.string.delete),
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    },
                                    onClick = {
                                        onMenuOpened(false)

                                        event(Event.OnDeleteClick(account.id))
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun AccountManagementPreview() {
    AccountManagementScreen(state = State(flowOf(PagingData.from((0..10).map {
        UserModel("$it", "$it$it$it$it", null)
    })))) {}
}
