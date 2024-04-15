package shov.studio.ui.anime.ui.details.addtolist

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
import shov.studio.enums.Status
import shov.studio.domain.core.usecases.UpdateStatusUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class DetailsAddToListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateStatusUseCase: UpdateStatusUseCase,
) : ViewModel(), DetailsAddToListContract {
    private val _state = MutableStateFlow(
        DetailsAddToListContract.State(
            checkNotNull(savedStateHandle[AnimeRoute.Args.status]),
            checkNotNull(savedStateHandle[AnimeRoute.Args.type])
        )
    )
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DetailsAddToListContract.Effect>()
    override val effect = _effect.asSharedFlow()

    private fun onSetClick() {
        _state.update { state -> state.loading() }

        updateStatus(state.value.chosenStatus)
    }

    private fun updateStatus(status: Status) = viewModelScope.launch {
        updateStatusUseCase(
            checkNotNull(savedStateHandle[AnimeRoute.Args.type]),
            checkNotNull(savedStateHandle[AnimeRoute.Args.id]),
            status
        ).onSuccess { _effect.emit(DetailsAddToListContract.Effect.OnStatusSet(status)) }
            .onFailure { error -> _state.update { state -> state.onError(error.toErrorString()) } }
    }

    override fun event(event: DetailsAddToListContract.Event) {
        when (event) {
            DetailsAddToListContract.Event.OnSetClick -> onSetClick()

            is DetailsAddToListContract.Event.OnStatusClick ->
                _state.update { state -> state.onStatusSet(event.status) }

            DetailsAddToListContract.Event.OnCloseClick -> viewModelScope.launch {
                _effect.emit(DetailsAddToListContract.Effect.OnClose)
            }
        }
    }
}
