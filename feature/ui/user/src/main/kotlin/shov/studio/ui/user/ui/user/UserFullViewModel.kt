package shov.studio.ui.user.ui.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.mvi.keepers.IntentKeeper
import core.ui.error.toErrorString
import shov.studio.domain.auth.usecases.AuthFullUserUseCase
import shov.studio.domain.auth.usecases.GetAuthLinkUseCase
import shov.studio.domain.auth.usecases.IsAuthorizedUseCase
import shov.studio.domain.user.usecases.GetFullUserInfoUseCase
import shov.studio.feature.ui.models.data.converters.toUserFullModel
import shov.studio.ui.user.ui.user.UserContract.Effect
import shov.studio.ui.user.ui.user.UserContract.Event
import shov.studio.ui.user.ui.user.UserContract.State
import shov.studio.ui.user.ui.user.UserContract.Status
import javax.inject.Inject

@HiltViewModel
internal class UserFullViewModel @Inject constructor(
    private val getFullUserInfoUseCase: GetFullUserInfoUseCase,
    private val authFullUserUseCase: AuthFullUserUseCase,
    val isAuthorizedUseCase: IsAuthorizedUseCase,
    val getAuthLinkUseCase: GetAuthLinkUseCase,
    private val intentKeeper: IntentKeeper,
) : ViewModel(), UserContract {
    private val _state = MutableStateFlow(State())
    override val state =
        _state.stateIn(viewModelScope, SharingStarted.Eagerly, State())

    private val effectFlow = MutableSharedFlow<Effect>()
    override val effect = effectFlow.asSharedFlow()

    override fun event(event: Event) {
        when (event) {
            Event.OnLinkClick -> viewModelScope.launch {
                effectFlow.emit(Effect.OnLinkLoaded(getAuthLinkUseCase()))
            }

            Event.OnLoadClick -> load()
            Event.OnRefresh -> refresh()
            Event.OnLogOutClick -> {
                _state.update { state -> state.copy(isMenuOpened = false) }

                effect(Effect.OnLogOut(state.value.user.id.toString()))
            }

            Event.OnMenuCloseClick -> _state.update { state -> state.copy(isMenuOpened = false) }
            Event.OnMenuOpenClick -> _state.update { state -> state.copy(isMenuOpened = true) }
        }
    }

    private fun effect(effect: Effect) {
        viewModelScope.launch { effectFlow.emit(effect) }
    }

    init {
        viewModelScope.launch {
            if (isAuthorizedUseCase()) load()
            else _state.update { state -> state.copy(status = Status.NEED_AUTH) }

            intentKeeper.observe().collectLatest { intent ->
                intent.data?.let { uri ->
                    _state.update { state -> state.copy(status = Status.LOADING) }

                    authFullUserUseCase(uri).onSuccess { userFullEntity ->
                        _state.update { state ->
                            state.copy(
                                status = Status.COMPLETE,
                                user = userFullEntity.toUserFullModel(),
                                error = null
                            )
                        }

                        effect(Effect.OnRefreshFinish)
                    }.onFailure { throwable ->
                        _state.update { state ->
                            state.copy(
                                status = Status.ERROR,
                                error = throwable.toErrorString()
                            )
                        }

                        effect(Effect.OnRefreshFinish)
                    }
                } ?: run {
                    _state.update { state ->
                        state.copy(
                            status = if (isAuthorizedUseCase()) Status.ERROR else Status.AUTH_ERROR,
                            error = "Unexpected error"
                        )
                    }

                    effect(Effect.OnRefreshFinish)
                }
            }
        }
    }

    private fun refresh() {
        _state.update { state -> state.copy(status = Status.REFRESH) }

        getUser()
    }

    private fun load() {
        _state.update { state -> state.copy(status = Status.LOADING) }

        getUser()
    }

    private fun getUser() {
        viewModelScope.launch {
            getFullUserInfoUseCase().onSuccess { userFullEntity ->
                _state.update { state ->
                    state.copy(
                        status = Status.COMPLETE,
                        user = userFullEntity.toUserFullModel(),
                        error = null
                    )
                }

                effect(Effect.OnRefreshFinish)
            }.onFailure { error ->
                _state.update { state ->
                    state.copy(status = Status.ERROR, error = error.toErrorString())
                }

                effect(Effect.OnRefreshFinish)
            }
        }
    }
}
