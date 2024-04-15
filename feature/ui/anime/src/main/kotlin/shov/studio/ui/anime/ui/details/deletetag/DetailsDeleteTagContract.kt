package shov.studio.ui.anime.ui.details.deletetag

import androidx.compose.runtime.Immutable
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagContract.Effect
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagContract.Event
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagContract.State

internal interface DetailsDeleteTagContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val tag: String,
        val error: String? = null,
        val isLoading: Boolean = false,
        val isLoaded: Boolean = false,
    ) {
        val isError = error != null
        val isButtonActive = isLoaded.not() and isLoading.not()

        fun loading() = copy(error = null, isLoading = true, isLoaded = false)
        fun onError(error: String) = copy(error = error, isLoaded = false, isLoading = false)
        fun success() = copy(error = null, isLoaded = true, isLoading = false)
    }

    @Immutable
    sealed class Event {
        data object OnDeleteClick : Event()
        data object OnCancelClick : Event()
    }

    sealed class Effect {
        data class OnDelete(val tag: String) : Effect()
        data object OnCancel : Effect()
    }
}
