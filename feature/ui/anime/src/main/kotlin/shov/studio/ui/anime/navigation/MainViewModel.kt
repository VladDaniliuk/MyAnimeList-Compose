package shov.studio.ui.anime.navigation

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
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import javax.inject.Inject

@HiltViewModel
internal class MainViewModel @Inject constructor(
    isAuthorizedFlowUseCase: IsAuthorizedFlowUseCase,
) : ViewModel(), MainContract {
    private val _state =
        MutableStateFlow(MainContract.State(false))
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MainContract.Effect>()
    override val effect = _effect.asSharedFlow()

    override fun event(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnItemClick ->
                effect(MainContract.Effect.OnItemOpened(event.type, event.id))

            is MainContract.Event.OnTabClick -> effect(MainContract.Effect.OnTabOpened(event.tab))
        }
    }

    fun effect(effect: MainContract.Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    init {
        viewModelScope.launch {
            isAuthorizedFlowUseCase().collectLatest { isAuthorized ->
                _state.update { state -> state.copy(isAuthorized = isAuthorized) }
            }
        }
    }
}
