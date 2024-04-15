package shov.studio.ui.anime.ui.userlist.main

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.userlist.main.UserListContract.Effect
import shov.studio.ui.anime.ui.userlist.main.UserListContract.Event
import shov.studio.ui.anime.ui.userlist.main.UserListContract.State

internal interface UserListContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val anime: SubState = SubState(),
        val manga: SubState = SubState(),
    )

    @Immutable
    data class SubState(
        val details: ImmutableList<DetailsStandardModel>,
        val status: Status, val error: String?,
    ) {
        fun loading() = SubState()

        constructor() : this(details = (1..10).map { id ->
            DetailsStandardModel(id, "", null, null, "")
        }.toImmutableList(), status = Status.LOADING, error = null)
    }

    enum class Status {
        LOADING, COMPLETE, ERROR
    }

    @Immutable
    sealed interface Event {
        data class OnListClick(val type: String) : Event
        data class OnItemClick(val type: String, val id: Int) : Event
        data object OnLoadRetryClick : Event
    }

    sealed interface Effect {
        data class OnListOpen(val type: String) : Effect
        data class OnItemOpen(val type: String, val id: Int) : Effect
    }
}
