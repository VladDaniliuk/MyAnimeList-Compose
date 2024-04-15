package shov.studio.ui.anime.ui.details.addtag

import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagContract.Effect
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagContract.Event
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagContract.State
import javax.annotation.concurrent.Immutable

internal interface DetailsAddTagContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val tag: String = "",
        val error: String? = null,
        val isLoading: Boolean = false,
        val isLoaded: Boolean = false,
    ) {
        val isReadOnly = isLoaded or isLoading
        val isCancelEnabled = (isLoaded or isLoading).not()
        val isAddActive = isLoaded.not() and isLoading.not() and tag.isNotEmpty()
        val isError = error != null

        fun onTagChanged(tag: String) = copy(tag = tag, error = null)

        fun onError(error: String) = copy(error = error, isLoading = false)

        fun onLoading() = copy(error = null, isLoading = true)

        fun onLoaded() = copy(error = null, isLoading = false, isLoaded = true)
    }

    @Immutable
    sealed class Event {
        data class OnTagChange(val tag: String) : Event()
        data object OnAddClick : Event()
        data object OnBackClick : Event()
    }

    sealed class Effect {
        data class OnAdded(val tag: String) : Effect()
        data object OnClose : Effect()
    }
}
