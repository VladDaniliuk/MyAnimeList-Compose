package core.ui.common.loaderror

import android.accounts.NetworkErrorException
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import core.ui.R
import core.ui.error.toErrorString

@Composable
fun LazyItemScope.LoadErrorView(
    loadStateError: LoadState.Error,
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onRefresh: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillParentMaxWidth()
            .padding(paddingValues),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = loadStateError.error.toErrorString(),
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier.padding(top = 8.dp),
            onClick = onRefresh
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
fun LoadErrorPreview() {
    LazyColumn {
        item {
            LoadErrorView(
                loadStateError = LoadState.Error(NetworkErrorException("We are currently under scheduled maintenance until 3:00 am Dec 22 PDT."))
            ) {}
        }
    }
}
