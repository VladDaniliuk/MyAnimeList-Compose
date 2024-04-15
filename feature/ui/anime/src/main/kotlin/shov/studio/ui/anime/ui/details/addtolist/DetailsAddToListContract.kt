package shov.studio.ui.anime.ui.details.addtolist

import androidx.compose.runtime.Immutable
import shov.studio.enums.Status
import core.mvi.UnidirectionalViewModel
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.ui.details.addtolist.DetailsAddToListContract.Effect
import shov.studio.ui.anime.ui.details.addtolist.DetailsAddToListContract.Event
import shov.studio.ui.anime.ui.details.addtolist.DetailsAddToListContract.State

internal interface DetailsAddToListContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val chosenStatus: Status,
        val type: String,
        val error: String? = null,
        val isLoading: Boolean = false,
    ) {
        fun isChosenStatus(status: Status) = status == chosenStatus
        val isError = error != null
        val isAnime = type == AnimeRoute.Type.anime
    }

    fun State.onStatusSet(status: Status) = copy(error = null, chosenStatus = status)
    fun State.loading() = copy(error = null, isLoading = true)
    fun State.onError(error: String) = copy(error = error, isLoading = false)

    @Immutable
    sealed class Event {
        data class OnStatusClick(val status: Status) : Event()
        data object OnSetClick : Event()
        data object OnCloseClick : Event()
    }

    sealed class Effect {
        data object OnClose : Effect()
        data class OnStatusSet(val status: Status) : Effect()
    }
}
