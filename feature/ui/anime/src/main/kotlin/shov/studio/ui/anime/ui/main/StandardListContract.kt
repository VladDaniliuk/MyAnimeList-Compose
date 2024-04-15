package shov.studio.ui.anime.ui.main

import androidx.compose.runtime.Stable
import androidx.paging.PagingData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.main.StandardListContract.Effect
import shov.studio.ui.anime.ui.main.StandardListContract.Event
import shov.studio.ui.anime.ui.main.StandardListContract.State

internal sealed interface StandardListContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val type: String,
        val detailsPagingList: ImmutableList<Flow<PagingData<DetailsStandardModel>>>,
        val query: String = "",
        val isSearchBarActive: Boolean = false,
        val isAuthorized: Boolean = false,
    )

    @Stable
    sealed interface Event {
        data class OnQueryChange(val query: String) : Event
        data object OnFilterClick : Event
        data object OnSettingsClick : Event
        data class OnItemClick(val id: Int) : Event
        data class OnAddToListClick(val id: Int, val type: String) : Event
        data class OnSearchBarClick(val isOpened: Boolean) : Event
    }

    sealed interface Effect {
        data object OnFilterOpen : Effect
        data class OnItemOpen(val id: Int) : Effect
        data object OnSettingsOpen : Effect
        data class OnAddToList(val id: Int, val type: String) : Effect
    }
}
