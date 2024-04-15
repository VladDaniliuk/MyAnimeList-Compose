package shov.studio.ui.user.ui.accauntmanagement

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import core.mvi.UnidirectionalViewModel
import shov.studio.feature.ui.models.data.models.UserModel
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract.Effect
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract.Event
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract.State

internal sealed interface AccountManagementContract :
    UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(val list: Flow<PagingData<UserModel>>)

    @Stable
    sealed interface Event {
        data class OnDeleteClick(val id: String) : Event
        data class OnDefaultClick(val id: String) : Event
        data object OnBackClick : Event
    }

    sealed interface Effect {
        data object OnBack : Effect
    }
}
