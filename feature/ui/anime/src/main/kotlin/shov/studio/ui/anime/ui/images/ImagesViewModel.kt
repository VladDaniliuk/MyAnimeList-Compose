package shov.studio.ui.anime.ui.images

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.domain.core.usecases.GetImagesUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class ImagesViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ImagesUiState(index = checkNotNull(savedStateHandle[AnimeRoute.Args.index]))
    )
    val uiState = _uiState.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        ImagesUiState(index = checkNotNull(savedStateHandle[AnimeRoute.Args.index]))
    )

    init {
        _uiState.update { uiState ->
            uiState.copy(status = ImagesUiState.LoadStatus.LOADING)
        }

        viewModelScope.launch {
            getImagesUseCase(
                checkNotNull(savedStateHandle[AnimeRoute.Args.type]),
                checkNotNull(savedStateHandle[AnimeRoute.Args.id])
            ).onSuccess { entity ->
                _uiState.update { uiState ->
                    uiState.copy(
                        images = entity.toImmutableList(),
                        status = ImagesUiState.LoadStatus.COMPLETE
                    )
                }
            }.onFailure { error ->
                _uiState.update { uiState ->
                    uiState.copy(
                        error = error.toErrorString(),
                        status = ImagesUiState.LoadStatus.ERROR
                    )
                }
            }
        }
    }
}
