package shov.studio.ui.anime.ui.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.details.DetailsContract
import core.ui.preview.PreviewScreenSizes

@Composable
internal fun DetailsCommentEditorView(
    detailsState: DetailsContract.State,
    event: (DetailsContract.Event) -> Unit,
) {
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            TextField(
                value = detailsState.details.listStatus.comments,
                onValueChange = { text ->
                    event(DetailsContract.Event.OnCommentChange(text))
                },
                shape = MaterialTheme.shapes.medium,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                placeholder = {
                    Text(text = stringResource(R.string.type_here))
                },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                enabled = detailsState.details.listStatus.comments.isEmpty().not(),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End),
                onClick = {
                    event(DetailsContract.Event.OnCommentSetClick)
                }
            ) {
                Text(text = stringResource(R.string.send))
            }
        }
    }
}

@PreviewScreenSizes
@Composable
private fun DetailsCommentEditorPreview() {
    DetailsCommentEditorView(event = {}, detailsState = DetailsContract.State("anime"))
}
