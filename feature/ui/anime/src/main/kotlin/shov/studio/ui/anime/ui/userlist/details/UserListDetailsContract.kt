package shov.studio.ui.anime.ui.userlist.details

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import shov.studio.enums.Sort
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.Effect
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.Event
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.State

internal interface UserListDetailsContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val type: String,
        val sort: Sort,
        val watching: Flow<PagingData<DetailsStandardModel>> = flowOf(),
        val completed: Flow<PagingData<DetailsStandardModel>> = flowOf(),
        val hold: Flow<PagingData<DetailsStandardModel>> = flowOf(),
        val dropped: Flow<PagingData<DetailsStandardModel>> = flowOf(),
        val plan: Flow<PagingData<DetailsStandardModel>> = flowOf(),
        val isAuthorized: Boolean = false,
    )

    @Stable
    sealed interface Event {
        data class OnItemClick(val id: Int) : Event
        data class OnAddToListClick(val id: Int) : Event
        data object OnBackClick : Event
        data object OnSortClick : Event
    }

    sealed interface Effect {
        data class OnItemOpen(val type: String, val id: Int) : Effect
        data class OnAddToListOpen(val type: String, val id: Int) : Effect
        data object OnBack : Effect
        data class OnSortOpen(val type: String, val sort: Sort) : Effect
    }
}
