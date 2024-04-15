package shov.studio.ui.anime.ui.userlist.sort

import androidx.compose.runtime.Immutable
import shov.studio.enums.Sort
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.ui.userlist.sort.UserListSortContract.Effect
import shov.studio.ui.anime.ui.userlist.sort.UserListSortContract.Event
import shov.studio.ui.anime.ui.userlist.sort.UserListSortContract.State

internal interface UserListSortContract :
    UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(val type: String, val sort: Sort) {
        fun isSelected(sort: Sort) = sort == this.sort
        fun isSelected(vararg sort: Sort) = sort.contains(this.sort)
    }

    @Immutable
    sealed interface Event {
        data class OnSortClick(val sort: Sort) : Event
        data object OnCancelClick : Event
        data object OnSaveClick : Event
    }

    sealed interface Effect {
        data object OnCancel : Effect
        data class OnSave(val sort: Sort) : Effect
    }
}