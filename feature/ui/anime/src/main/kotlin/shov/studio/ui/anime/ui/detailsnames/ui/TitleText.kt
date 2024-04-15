package shov.studio.ui.anime.ui.detailsnames.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesContract.Status
import core.ui.common.modifiers.placeholder

@Composable
internal fun TitleText(
    title: String,
    text: String,
    status: Status,
    modifier: Modifier = Modifier,
) {
    val clipboardManager = LocalClipboardManager.current

    if (text.isNotEmpty() or (status == Status.LOADING)) {
        Row(modifier = modifier.padding(bottom = 8.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(text = title, style = MaterialTheme.typography.titleLarge)

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .placeholder(enabled = status == Status.LOADING),
                    text = text,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            IconButton(
                modifier = Modifier.align(Alignment.CenterVertically),
                enabled = status == Status.COMPLETE,
                onClick = { clipboardManager.setText(AnnotatedString(text)) }
            ) {
                Icon(
                    imageVector = Icons.Rounded.ContentCopy,
                    contentDescription = Icons.Rounded.ContentCopy.name
                )
            }
        }
    }
}

@Preview
@Composable
private fun TitleTextPreview() {
    TitleText(title = "Title", text = "Title Text", status = Status.LOADING)
}

@Preview
@Composable
private fun TitleTextPlaceholderPreview() {
    TitleText(title = "Title", text = "Title Text", status = Status.ERROR)
}
