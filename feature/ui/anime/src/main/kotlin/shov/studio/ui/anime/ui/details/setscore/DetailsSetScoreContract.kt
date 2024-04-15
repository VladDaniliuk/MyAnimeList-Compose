package shov.studio.ui.anime.ui.details.setscore

import androidx.compose.runtime.Immutable
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreContract.Effect
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreContract.Event
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreContract.State

internal interface DetailsSetScoreContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val score: Float,
        val error: String? = null,
        val isLoading: Boolean = false,
        val isLoaded: Boolean = false,
    ) {
        fun setScore(score: Float) = copy(score = score, error = null)
        fun onLoading() = copy(error = null, isLoading = true)
        fun onError(error: String) = copy(error = error, isLoading = false)
        fun onSuccess() = copy(error = null, isLoading = false, isLoaded = true)

        val isError = error != null
    }

    @Immutable
    sealed class Event {
        data class OnScoreChanged(val score: Float) : Event()
        data object OnSetClick : Event()
        data object OnBackClick : Event()
    }

    sealed class Effect {
        data object OnCancel : Effect()
        data class OnClose(val score: Int) : Effect()
    }
}
