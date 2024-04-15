package shov.studio.ui.anime.ui.details.setscore

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.domain.core.usecases.UpdateScoreUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreContract.Effect
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreContract.Event
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreContract.State
import core.ui.error.toErrorString
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
internal class DetailsSetScoreViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateScoreUseCase: UpdateScoreUseCase,
) : ViewModel(), DetailsSetScoreContract {
    private val _state =
        MutableStateFlow(State(requireNotNull(savedStateHandle[AnimeRoute.Args.score])))
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    private fun updateScore() {
        _state.update(State::onLoading)

        viewModelScope.launch {
            viewModelScope.launch {
                updateScoreUseCase(
                    requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
                    requireNotNull(savedStateHandle[AnimeRoute.Args.id]),
                    state.value.score.roundToInt()
                ).onSuccess { entity ->
                    _state.update(State::onSuccess)
                    viewModelScope.launch { _effect.emit(Effect.OnClose(entity.score)) }
                }.onFailure { error ->
                    _state.update { state -> state.onError(error.toErrorString()) }
                }
            }
        }
    }

    override fun event(event: Event) {
        when (event) {
            is Event.OnScoreChanged -> _state.update { state -> state.setScore(event.score) }
            Event.OnSetClick -> updateScore()
            Event.OnBackClick -> viewModelScope.launch { _effect.emit(Effect.OnCancel) }
        }
    }
}
