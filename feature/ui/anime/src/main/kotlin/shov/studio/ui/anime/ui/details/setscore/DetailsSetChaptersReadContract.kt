package shov.studio.ui.anime.ui.details.setscore

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.ui.details.setscore.DetailsSetChaptersReadContract.Effect
import shov.studio.ui.anime.ui.details.setscore.DetailsSetChaptersReadContract.Event
import shov.studio.ui.anime.ui.details.setscore.DetailsSetChaptersReadContract.State

internal interface DetailsSetChaptersReadContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        @StringRes val headerId: Int,
        val max: Int,
        val value: String = "",
        val error: String? = null,
        val isLoading: Boolean = false,
    ) {
        val isError = error != null
        val canSave = value.toIntOrNull() != null
    }

    fun State.onValueSet(value: String) = copy(value = value, error = null)
    fun State.loading() = copy(error = null, isLoading = true)
    fun State.onError(error: String) = copy(error = error, isLoading = false)

    @Immutable
    sealed class Event {
        data class OnValueChange(val value: String) : Event()
        data object OnSaveClick : Event()
        data object OnCancelClick : Event()
    }

    sealed class Effect {
        data class OnClose(val value: Int) : Effect()
        data object OnCancel : Effect()
    }
}
