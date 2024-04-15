package shov.studio.ui.user.ui.user.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.ui.user.R
import shov.studio.ui.user.ui.user.UserContract.Event


@Composable
internal fun UserNeedAuthView(event: (Event) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = { event(Event.OnLinkClick) }
        ) {
            Text(text = stringResource(R.string.sign_in))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserNeedAuthPreview() {
    UserNeedAuthView {}
}
