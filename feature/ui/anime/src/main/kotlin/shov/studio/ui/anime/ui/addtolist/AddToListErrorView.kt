package shov.studio.ui.anime.ui.addtolist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R

@Composable
internal fun AddToListErrorView(state: AddToListContract.State, event: (AddToListContract.Event) -> Unit) {
    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.error),
                style = MaterialTheme.typography.headlineSmall
            )

            state.error?.let { error ->
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    text = error,
                    textAlign = TextAlign.Center
                )
            }

            TextButton(
                modifier = Modifier.padding(top = 24.dp),
                onClick = { event(AddToListContract.Event.OnRetryClick) }
            ) {
                Text(text = stringResource(R.string.retry))
            }
        }
    }
}
