package shov.studio.ui.user.ui.logout

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
import shov.studio.domain.auth.usecases.DeleteAccountUseCase
import shov.studio.domain.auth.usecases.GetAccountByIdUseCase
import shov.studio.feature.ui.models.data.converters.toModel
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.user.ui.logout.LogOutContract.Effect
import shov.studio.ui.user.ui.logout.LogOutContract.State
import shov.studio.ui.user.ui.logout.LogOutContract.Status
import javax.inject.Inject

@HiltViewModel
internal class LogOutViewModel @Inject constructor(
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
) : ViewModel(), LogOutContract {
    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: LogOutContract.Event) {
        when (event) {
            LogOutContract.Event.OnCancelClick -> effect(Effect.OnCancel)
            LogOutContract.Event.OnLogOutClick -> {
                _state.update { state -> state.copy(status = Status.DELETING) }

                viewModelScope.launch {
                    deleteAccountUseCase(state.value.user.id)

                    effect(Effect.OnCancel)
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    user = getAccountByIdUseCase(
                        requireNotNull(savedStateHandle[AnimeRoute.Args.id])
                    ).toModel(),
                    status = Status.LOADED
                )
            }
        }
    }
}
