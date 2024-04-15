package shov.studio.ui.user.ui.user.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.ui.user.R
import shov.studio.ui.user.ui.user.UserContract
import shov.studio.ui.user.ui.user.UserContract.Event
import shov.studio.ui.user.ui.user.UserContract.State

@Composable
internal fun UserInfoError(state: State, event: (Event) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.error))

        state.error?.let { error ->
            Text(textAlign = TextAlign.Center, text = error)
        }

        Button(onClick = { event(Event.OnLoadClick) }) {
            Text(text = stringResource(id = R.string.try_again))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UserInfoErrorPreview() {
    UserInfoError(state = State(status = UserContract.Status.ERROR)) {}
}
