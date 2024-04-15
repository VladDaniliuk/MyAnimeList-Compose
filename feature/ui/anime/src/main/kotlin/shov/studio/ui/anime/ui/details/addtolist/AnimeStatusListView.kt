package shov.studio.ui.anime.ui.details.addtolist

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.enums.Status
import shov.studio.ui.anime.R

@Composable
internal fun AnimeStatusListView(
    state: DetailsAddToListContract.State,
    event: (DetailsAddToListContract.Event) -> Unit,
) {
    Column {
        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.delete.id),
            supportedText = stringResource(R.string.deleting_other_info_about_anime),
            isSelected = state.isChosenStatus(Status.delete),
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.delete))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.not_watching.id),
            isSelected = state.isChosenStatus(Status.not_watching),
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.not_watching))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.watching.id),
            isSelected = state.isChosenStatus(Status.watching)
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.watching))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.completed.id),
            isSelected = state.isChosenStatus(Status.completed)
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.completed))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.on_hold.id),
            isSelected = state.isChosenStatus(Status.on_hold)
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.on_hold))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.dropped.id),
            isSelected = state.isChosenStatus(Status.dropped)
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.dropped))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.plan_to_watch.id),
            isSelected = state.isChosenStatus(Status.plan_to_watch)
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.plan_to_watch))
        }
    }
}

@Preview
@Composable
fun AnimeStatusListPreview() {
    Surface {
        AnimeStatusListView(state = DetailsAddToListContract.State(Status.not_watching, "anime")) {}
    }
}
