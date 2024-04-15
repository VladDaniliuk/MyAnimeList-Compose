package shov.studio.ui.anime.ui.detailsnames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesContract.Event
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesContract.State
import shov.studio.ui.anime.ui.detailsnames.ui.TitleText

@Composable
internal fun DetailsNamesScreen(state: State, event: (Event) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
                .size(width = 32.dp, height = 4.dp)
                .background(MaterialTheme.colorScheme.onSurfaceVariant, MaterialTheme.shapes.small)
        )

        when (state.status) {
            DetailsNamesContract.Status.LOADING, DetailsNamesContract.Status.COMPLETE -> {
                TitleText(
                    title = stringResource(R.string.title),
                    text = state.names.title,
                    status = state.status
                )

                TitleText(
                    title = stringResource(R.string.english),
                    text = state.names.en,
                    status = state.status
                )

                TitleText(
                    title = stringResource(R.string.japan),
                    text = state.names.ja,
                    status = state.status
                )

                TitleText(
                    title = stringResource(R.string.alternative_names),
                    text = state.names.synonyms.joinToString("\n"),
                    status = state.status
                )
            }

            DetailsNamesContract.Status.ERROR -> {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(top = 32.dp),
                    text = state.error!!,
                    textAlign = TextAlign.Center
                )

                TextButton(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 32.dp)
                        .navigationBarsPadding(),
                    onClick = { event(Event.OnRetryClick) }
                ) {
                    Text(text = stringResource(id = R.string.retry))
                }
            }
        }
    }
}

@Preview
@Composable
fun DetailsNamesPreview() {
    DetailsNamesScreen(State()) {}
}
