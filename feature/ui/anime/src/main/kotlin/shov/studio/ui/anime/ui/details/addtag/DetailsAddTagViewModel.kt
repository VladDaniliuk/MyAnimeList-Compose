package shov.studio.ui.anime.ui.details.addtag

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
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagContract.Effect
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagContract.Event
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagContract.State
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class DetailsAddTagViewModel @Inject constructor(
    private val updateTagsUseCase: UpdateTagsUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), DetailsAddTagContract {
    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private val effectFlow = MutableSharedFlow<Effect>()
    override val effect = effectFlow.asSharedFlow()

    private fun setTag(tag: String) {
        _state.update { state -> state.onTagChanged(tag) }
    }

    private fun updateTags() {
        _state.update { state -> state.onLoading() }

        viewModelScope.launch {
            updateTagsUseCase(
                requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
                requireNotNull(savedStateHandle[AnimeRoute.Args.id]),
                (savedStateHandle.get<Array<String>>(AnimeRoute.Args.tags)?.toList()
                    ?: emptyList()) + state.value.tag
            ).onSuccess {
                _state.update { state -> state.onLoaded() }

                viewModelScope.launch { effectFlow.emit(Effect.OnAdded(state.value.tag)) }
            }.onFailure { error -> _state.update { state -> state.onError(error.toErrorString()) } }
        }
    }

    override fun event(event: Event) {
        when (event) {
            is Event.OnAddClick -> updateTags()
            is Event.OnTagChange -> setTag(event.tag)
            Event.OnBackClick -> viewModelScope.launch { effectFlow.emit(Effect.OnClose) }
        }
    }
}
