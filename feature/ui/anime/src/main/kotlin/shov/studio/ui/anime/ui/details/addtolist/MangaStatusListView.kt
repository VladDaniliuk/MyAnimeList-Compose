package shov.studio.ui.anime.ui.details.addtolist

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import shov.studio.enums.Status
import shov.studio.ui.anime.R
import core.ui.preview.PreviewScreenSizes

@Composable
internal fun MangaStatusListView(
    state: DetailsAddToListContract.State,
    event: (DetailsAddToListContract.Event) -> Unit,
) {
    Column {
        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.delete.id),
            supportedText = stringResource(R.string.deleting_other_info_about_manga),
            isSelected = state.isChosenStatus(Status.delete),
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.delete))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.not_reading.id),
            isSelected = state.isChosenStatus(Status.not_reading),
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.not_reading))
        }

        StatusItemView(
            enabled = state.isLoading.not(),
            text = stringResource(Status.reading.id),
            isSelected = state.isChosenStatus(Status.reading)
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.reading))
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
            text = stringResource(Status.plan_to_read.id),
            isSelected = state.isChosenStatus(Status.plan_to_read)
        ) {
            event.invoke(DetailsAddToListContract.Event.OnStatusClick(Status.plan_to_read))
        }
    }
}

@PreviewScreenSizes
@Composable
fun MangaStatusListPreview() {
    Surface {
        MangaStatusListView(
            state = DetailsAddToListContract.State(Status.not_reading, "manga")
        ) {}
    }
}
