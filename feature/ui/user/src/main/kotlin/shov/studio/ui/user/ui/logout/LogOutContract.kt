package shov.studio.ui.user.ui.logout

import androidx.compose.runtime.Stable
import core.mvi.UnidirectionalViewModel
import shov.studio.feature.ui.models.data.models.UserModel
import shov.studio.ui.user.ui.logout.LogOutContract.Effect
import shov.studio.ui.user.ui.logout.LogOutContract.Event
import shov.studio.ui.user.ui.logout.LogOutContract.State

internal sealed interface LogOutContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val user: UserModel = UserModel("", "", null),
        val status: Status = Status.LOADING,
    )

    enum class Status {
        LOADING, LOADED, DELETING
    }

    @Stable
    sealed interface Event {
        data object OnCancelClick : Event
        data object OnLogOutClick : Event
    }

    sealed interface Effect {
        data object OnCancel : Effect
    }
}
