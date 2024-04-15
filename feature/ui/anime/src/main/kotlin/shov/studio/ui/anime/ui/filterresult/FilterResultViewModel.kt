package shov.studio.ui.anime.ui.filterresult

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.enums.Season
import shov.studio.enums.SortType
import shov.studio.domain.anime.usecases.GetSeasonUseCase
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import shov.studio.domain.core.data.models.NodeStandardEntity
import core.utils.mapResponse
import shov.studio.ui.anime.data.converters.toDetailsStandardModel
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.Effect
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.Event
import shov.studio.ui.anime.ui.filterresult.FilterResultContract.State
import javax.inject.Inject

@HiltViewModel
internal class FilterResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getSeasonUseCase: GetSeasonUseCase,
    isAuthorizedFlowUseCase: IsAuthorizedFlowUseCase,
) : FilterResultContract, ViewModel() {
    private val _state = MutableStateFlow(
        State(
            false,
            getSeasonUseCase(
                checkNotNull(savedStateHandle["year"]),
                Season.valueOf(checkNotNull(savedStateHandle["season"])),
                SortType.valueOf(checkNotNull(savedStateHandle["sort"]))
            )
                .mapResponse(NodeStandardEntity::toDetailsStandardModel)
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
            is Event.OnAddToListClick -> effect(Effect.OnAddToList(event.id))
            is Event.OnAnimeClick -> effect(Effect.OnAnimeOpen(event.id))
            Event.OnBackClick -> effect(Effect.OnBack)
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

