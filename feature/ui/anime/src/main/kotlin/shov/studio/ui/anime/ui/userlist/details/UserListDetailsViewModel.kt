package shov.studio.ui.anime.ui.userlist.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.enums.Sort
import shov.studio.enums.Status
import core.navigator.utils.getAndRemove
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import shov.studio.domain.core.data.models.NodeStandardEntity
import shov.studio.domain.core.usecases.GetUserListUseCase
import core.utils.mapResponse
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toDetailsStandardModel
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.Effect
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.Event
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract.State
import javax.inject.Inject

@HiltViewModel
internal class UserListDetailsViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    savedStateHandle: SavedStateHandle,
    private val isAuthorizedFlowUseCase: IsAuthorizedFlowUseCase,
) : ViewModel(), UserListDetailsContract {
    val type: String = requireNotNull(savedStateHandle[AnimeRoute.Args.type])

    private val _state = MutableStateFlow(
        State(
            type,
            Sort.list_updated_at,
            getUserListUseCase(
                type,
                if (type == AnimeRoute.Type.anime) Status.watching else Status.reading,
                Sort.list_updated_at
            ).mapResponse(NodeStandardEntity::toDetailsStandardModel).cachedIn(viewModelScope),
            getUserListUseCase(type, Status.completed, Sort.list_updated_at)
                .mapResponse(NodeStandardEntity::toDetailsStandardModel).cachedIn(viewModelScope),
            getUserListUseCase(type, Status.on_hold, Sort.list_updated_at)
                .mapResponse(NodeStandardEntity::toDetailsStandardModel).cachedIn(viewModelScope),
            getUserListUseCase(type, Status.dropped, Sort.list_updated_at)
                .mapResponse(NodeStandardEntity::toDetailsStandardModel).cachedIn(viewModelScope),
            getUserListUseCase(
                type,
                if (type == AnimeRoute.Type.anime) Status.plan_to_watch else Status.plan_to_read,
                Sort.list_updated_at
            ).mapResponse(NodeStandardEntity::toDetailsStandardModel).cachedIn(viewModelScope)
        )
    )
    override val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: Event) {
        when (event) {
            Event.OnBackClick -> effect(Effect.OnBack)
            is Event.OnItemClick -> effect(Effect.OnItemOpen(type, event.id))
            Event.OnSortClick -> effect(Effect.OnSortOpen(type, state.value.sort))
            is Event.OnAddToListClick -> effect(Effect.OnAddToListOpen(type, event.id))
        }
    }

    fun onSavedStateChanged(savedStateHandle: SavedStateHandle) {
        savedStateHandle.getAndRemove(AnimeRoute.Args.sort) { sort: Sort ->
            if (sort != state.value.sort)
                _state.update { state ->
                    state.copy(
                        sort = sort, watching = getUserListUseCase(
                            type,
                            if (type == AnimeRoute.Type.anime) Status.watching else Status.reading,
                            sort
                        ).mapResponse(NodeStandardEntity::toDetailsStandardModel)
                            .cachedIn(viewModelScope),
                        completed = getUserListUseCase(type, Status.completed, sort)
                            .mapResponse(NodeStandardEntity::toDetailsStandardModel)
                            .cachedIn(viewModelScope),
                        hold = getUserListUseCase(type, Status.on_hold, sort)
                            .mapResponse(NodeStandardEntity::toDetailsStandardModel)
                            .cachedIn(viewModelScope),
                        dropped = getUserListUseCase(type, Status.dropped, sort)
                            .mapResponse(NodeStandardEntity::toDetailsStandardModel)
                            .cachedIn(viewModelScope),
                        plan = getUserListUseCase(
                            type,
                            if (type == AnimeRoute.Type.anime) Status.plan_to_watch
                            else Status.plan_to_read,
                            sort
                        ).mapResponse(NodeStandardEntity::toDetailsStandardModel)
                            .cachedIn(viewModelScope)
                    )
                }
        }
    }

    init {
        viewModelScope.launch {
            isAuthorizedFlowUseCase().collectLatest { isAuthorized ->
                _state.update { state -> state.copy(isAuthorized = isAuthorized) }
            }
        }
    }
}
