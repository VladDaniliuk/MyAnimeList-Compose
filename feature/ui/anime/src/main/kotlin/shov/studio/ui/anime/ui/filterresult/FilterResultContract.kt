package shov.studio.ui.anime.ui.filterresult

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.Effect
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.Event
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.State

internal interface FilterResultContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val isAuthorized: Boolean,
        val filterDetails: Flow<PagingData<DetailsStandardModel>>,
    )

    @Stable
    sealed interface Event {
        data object OnBackClick : Event
        data class OnAnimeClick(val id: Int) : Event
        data class OnAddToListClick(val id: Int) : Event
    }

    sealed interface Effect {
        data object OnBack : Effect
        data class OnAnimeOpen(val id: Int) : Effect
        data class OnAddToList(val id: Int) : Effect
    }
}
