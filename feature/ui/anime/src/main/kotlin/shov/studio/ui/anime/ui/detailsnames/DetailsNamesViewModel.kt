package shov.studio.ui.anime.ui.detailsnames

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
import core.mvi.keepers.NetworkKeeper
import shov.studio.domain.core.usecases.GetDetailsNamesUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toAlternativeTitlesModel
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class DetailsNamesViewModel @Inject constructor(
    private val getDetailsNamesUseCase: GetDetailsNamesUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val networkKeeper: NetworkKeeper,
) : ViewModel(), DetailsNamesContract {
    private val _state = MutableStateFlow(DetailsNamesContract.State())
    override val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<DetailsNamesContract.Effect>()
    override val effect = _effect.asSharedFlow()

    override fun event(event: DetailsNamesContract.Event) {
        when (event) {
            DetailsNamesContract.Event.OnRetryClick -> getDetailsNames()
        }
    }

    private fun getDetailsNames() {
        _state.update { state ->
            state.copy(status = DetailsNamesContract.Status.LOADING, error = null)
        }

        viewModelScope.launch {
            getDetailsNamesUseCase(
                requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
                requireNotNull(savedStateHandle[AnimeRoute.Args.id])
            ).onSuccess { entity ->
                _state.update { state ->
                    state.copy(
                        names = entity.toAlternativeTitlesModel(),
                        status = DetailsNamesContract.Status.COMPLETE,
                        error = null
                    )
                }
            }.onFailure { error ->
                _state.update { state ->
                    state.copy(
                        status = DetailsNamesContract.Status.ERROR,
                        error = error.toErrorString()
                    )
                }
            }
        }
    }

    init {
        getDetailsNames()

        viewModelScope.launch {
            networkKeeper.observe().collectLatest { isConnected ->
                if (isConnected) {
                    if (state.value.status == DetailsNamesContract.Status.ERROR) {
                        getDetailsNames()
                    }
                }
            }
        }
    }
}
