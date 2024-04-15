package shov.studio.ui.anime.ui.details.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import core.ui.common.modifiers.placeholder

@Composable
internal fun DetailsSynopsisView(isLoading: Boolean, synopsis: String) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isOverflow by rememberSaveable { mutableStateOf(true) }

    Column(modifier = Modifier.padding(bottom = if (isLoading or (isOverflow or isExpanded).not()) 4.dp else 0.dp)) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp, end = 6.dp)
                .animateContentSize()
                .placeholder(enabled = isLoading),
            maxLines = if (isExpanded.not()) 3 else Int.MAX_VALUE,
            minLines = if (isLoading) 3 else 1,
            text = synopsis,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { textLayoutResult ->
                isOverflow = textLayoutResult.hasVisualOverflow
            }
        )

        if (isOverflow or isExpanded) TextButton(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally),
            onClick = { isExpanded = isExpanded.not() }
        ) {
            Text(text = stringResource(if (isExpanded) R.string.show_less else R.string.show_all))
        }
    }
}

@Preview
@Composable
private fun DetailsSynopsisPreview() {
    Surface {
        DetailsSynopsisView(
            isLoading = false,
            synopsis = "Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long Long "
        )
    }
}
