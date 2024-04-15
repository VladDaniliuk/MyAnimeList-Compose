package shov.studio.ui.anime.ui.details.deletetag

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
import shov.studio.domain.core.usecases.UpdateTagsUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagContract.Effect
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagContract.Event
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagContract.State
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class DetailsDeleteTagViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateTagsUseCase: UpdateTagsUseCase,
) : ViewModel(), DetailsDeleteTagContract {
    private val _state =
        MutableStateFlow(State(requireNotNull(savedStateHandle[AnimeRoute.Args.tag])))
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    private fun deleteTag(tags: List<String>) {
        _state.update(State::loading)

        viewModelScope.launch {
            updateTagsUseCase(
                requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
                requireNotNull(savedStateHandle[AnimeRoute.Args.id]),
                tags.minus(state.value.tag)
            ).onSuccess {
                _state.update { state -> state.success() }

                viewModelScope.launch {
                    _effect.emit(Effect.OnDelete(state.value.tag))
                }
            }.onFailure { error -> _state.update { state -> state.onError(error.toErrorString()) } }
        }
    }

    override fun event(event: Event) {
        when (event) {
            is Event.OnDeleteClick -> deleteTag(
                requireNotNull(savedStateHandle.get<Array<String>>(AnimeRoute.Args.tags)).toList()
            )

            Event.OnCancelClick -> viewModelScope.launch { _effect.emit(Effect.OnCancel) }
        }
    }
}
