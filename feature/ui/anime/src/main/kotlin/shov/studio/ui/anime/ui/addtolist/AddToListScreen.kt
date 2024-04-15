package shov.studio.ui.anime.ui.addtolist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.ui.anime.ui.addtolist.AddToListContract.Event
import shov.studio.ui.anime.ui.addtolist.AddToListContract.State

@Composable
fun AddToListScreen(state: State, event: (Event) -> Unit) {
    if (state.isError) {
        AddToListErrorView(state = state, event = event)
    } else {
        AddToListView(event = event, state = state)
    }
}

@Preview
@Composable
private fun AddToListPreview() {
    AddToListScreen(state = State(true)) {}
}
