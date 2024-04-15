package shov.studio.ui.ui.settings.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.settings.ui.R

@Composable
fun ColumnScope.SettingsNeedAuthView(onLogIn: () -> Unit) {
    Button(
        modifier = Modifier.align(Alignment.CenterHorizontally),
        onClick = onLogIn
    ) {
        Text(text = stringResource(R.string.log_in))
    }
}

@Preview
@Composable
fun SettingsNeedAuthPreview() {
    Column {
        SettingsNeedAuthView {}
    }
}
