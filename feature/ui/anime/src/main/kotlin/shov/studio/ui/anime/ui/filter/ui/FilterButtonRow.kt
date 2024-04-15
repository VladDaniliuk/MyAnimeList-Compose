package shov.studio.ui.anime.ui.filter.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R

@Composable
internal fun FilterButtonRow(onReset: () -> Unit, onConfirm: () -> Unit, modifier: Modifier = Modifier) {
    Row(modifier = modifier.padding(horizontal = 16.dp)) {
        OutlinedButton(
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            onClick = onReset
        ) {
            Text(text = stringResource(R.string.reset))
        }

        Button(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            onClick = onConfirm
        ) {
            Text(text = stringResource(R.string.confirm))
        }
    }
}

@Preview
@Composable
private fun FilterButtonRowPreview() {
    FilterButtonRow(onReset = { }, onConfirm = {})
}
