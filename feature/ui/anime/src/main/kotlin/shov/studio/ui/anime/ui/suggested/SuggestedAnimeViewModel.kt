package shov.studio.ui.anime.ui.suggested

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
import shov.studio.domain.anime.usecases.GetSuggestedAnimesUseCase
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import shov.studio.domain.core.data.models.NodeStandardEntity
import core.utils.mapResponse
import shov.studio.ui.anime.data.converters.toDetailsStandardModel
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.Effect
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.Event
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.State
import javax.inject.Inject

@HiltViewModel
internal class SuggestedAnimeViewModel @Inject constructor(
    getSuggestedAnimesUseCase: GetSuggestedAnimesUseCase,
    isAuthorizedFlowUseCase: IsAuthorizedFlowUseCase,
) : ViewModel(), SuggestedAnimeContract {

    private val _state = MutableStateFlow(
        State(
            getSuggestedAnimesUseCase().mapResponse(NodeStandardEntity::toDetailsStandardModel)
                .cachedIn(viewModelScope)
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
            is Event.OnAnimeClick -> effect(Effect.OnAnimeOpened(event.id))
            is Event.OnAddToListClick -> effect(Effect.OnAddToListOpened(event.id))
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
