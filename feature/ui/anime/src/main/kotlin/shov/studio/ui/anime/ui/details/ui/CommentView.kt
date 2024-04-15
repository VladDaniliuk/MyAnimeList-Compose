package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.details.DetailsContract
import core.ui.preview.PreviewScreenSizes

@Composable
internal fun CommentView(
    event: (DetailsContract.Event) -> Unit,
    detailsState: DetailsContract.State,
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 12.dp)
        ) {
            Text(
                text = stringResource(R.string.me),
                modifier = Modifier.weight(1f),
                style = MaterialTheme.typography.titleLarge
            )

            Box {
                IconButton(onClick = {
                    event(DetailsContract.Event.OnCommentMenuOpenClick)
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = stringResource(R.string.more)
                    )
                }

                DropdownMenu(
                    expanded = detailsState.isCommentMenuOpened,
                    onDismissRequest = {
                        event(DetailsContract.Event.OnCommentMenuCloseClick)
                    }
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.remove)) },
                        onClick = {
                            event(DetailsContract.Event.OnCommentDeleteClick)
                        }
                    )

                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.change)) },
                        onClick = {
                            event(DetailsContract.Event.OnCommentChangeClick)
                        }
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(horizontal = 12.dp),
            text = detailsState.details.listStatus.comments
        )
    }
}

@PreviewScreenSizes
@Composable
private fun CommentPreview() {
    CommentView(event = {}, detailsState = DetailsContract.State("anime"))
}
