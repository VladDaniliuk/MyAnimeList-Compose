package shov.studio.ui.ui.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.settings.ui.R

@Composable
fun ColumnScope.SettingsAuthErrorView(error: String?, onRetry: () -> Unit) {
    error?.let { textError ->
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = textError,
            textAlign = TextAlign.Center
        )
    }

    Button(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        onClick = onRetry
    ) {
        Text(text = stringResource(R.string.try_again))
    }
}

@Preview
@Composable
fun SettingsAuthErrorPreview() {
    Column {
        SettingsAuthErrorView(error = "Unexpected") {}
    }
}
