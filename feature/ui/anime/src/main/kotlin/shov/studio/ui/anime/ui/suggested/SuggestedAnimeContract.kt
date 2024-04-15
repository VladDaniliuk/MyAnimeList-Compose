package shov.studio.ui.anime.ui.suggested

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.Effect
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.Event
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.State

internal interface SuggestedAnimeContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val animes: Flow<PagingData<DetailsStandardModel>>,
        val isAuthorized: Boolean = false,
    )

    @Stable
    sealed interface Event {
        data class OnAnimeClick(val id: Int) : Event
        data class OnAddToListClick(val id: Int) : Event
    }

    sealed interface Effect {
        data class OnAnimeOpened(val id: Int) : Effect
        data class OnAddToListOpened(val id: Int) : Effect
    }
}
