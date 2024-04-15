package shov.studio.ui.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.core.data.Theme
import core.mvi.keepers.IntentKeeper
import core.ui.error.toErrorString
import shov.studio.domain.auth.usecases.AuthUserUseCase
import shov.studio.domain.auth.usecases.GetAuthLinkUseCase
import shov.studio.domain.auth.usecases.GetLastUsedAccountUseCase
import shov.studio.domain.auth.usecases.GetLastUsedAccountsUseCase
import shov.studio.domain.auth.usecases.IsAuthorizedUseCase
import shov.studio.domain.auth.usecases.OnUserChangeUseCase
import shov.studio.domain.usecases.GetIsDynamicColorUseCase
import shov.studio.domain.usecases.GetThemeUseCase
import shov.studio.domain.usecases.SetIsDynamicColorUseCase
import shov.studio.domain.usecases.SetThemeUseCase
import shov.studio.feature.domain.models.data.models.UserEntity
import shov.studio.feature.ui.models.data.converters.toModel
import shov.studio.ui.ui.settings.SettingsContract.Effect
import shov.studio.ui.ui.settings.SettingsContract.State
import shov.studio.ui.ui.settings.SettingsContract.Status
import javax.inject.Inject

@HiltViewModel
internal class SettingsViewModel @Inject constructor(
    getIsDynamicColorUseCase: GetIsDynamicColorUseCase,
    authUserUseCase: AuthUserUseCase,
    getThemeUseCase: GetThemeUseCase,
    intentKeeper: IntentKeeper,
    isAuthorizedUseCase: IsAuthorizedUseCase,
    private val getAuthLinkUseCase: GetAuthLinkUseCase,
    private val setIsDynamicColorUseCase: SetIsDynamicColorUseCase,
    private val setThemeUseCase: SetThemeUseCase,
    private val getLastUsedAccountsUseCase: GetLastUsedAccountsUseCase,
    private val onUserChangeUseCase: OnUserChangeUseCase,
    private val getLastUsedAccountUseCase: GetLastUsedAccountUseCase,
) : ViewModel(), SettingsContract {
    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    private fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: SettingsContract.Event) {
        when (event) {
            SettingsContract.Event.OnBackClick -> viewModelScope.launch { _effect.emit(Effect.OnBack) }
            SettingsContract.Event.OnLogInClick -> viewModelScope.launch {
                _effect.emit(Effect.OnLogIn(getAuthLinkUseCase()))
            }

            is SettingsContract.Event.OnThemExpandClick -> onThemeExpandChange(event.isExpanded)
            is SettingsContract.Event.OnThemeClick -> setTheme(event.theme)
            is SettingsContract.Event.OnDynamicColorChange -> viewModelScope.launch {
                setIsDynamicColorUseCase(event.isDynamic)
            }

            SettingsContract.Event.OnUserClick -> viewModelScope.launch { _effect.emit(Effect.OnUser) }
            SettingsContract.Event.OnUriClick -> viewModelScope.launch { _effect.emit(Effect.OnUriOpen) }
            is SettingsContract.Event.OnUserChangeClick -> viewModelScope.launch {
                onUserChangeUseCase(event.id)

                effect(Effect.OnAccountChanged)
            }

            SettingsContract.Event.OnUserListClick -> _state.update { state ->
                state.copy(isListOpened = state.isListOpened.not())
            }

            SettingsContract.Event.OnAccountManagementClick -> effect(Effect.OnAccountManagementOpen)

            SettingsContract.Event.OnAddNewAccountClick -> effect(Effect.OnLogIn(getAuthLinkUseCase()))
        }
    }

    private fun onThemeExpandChange(isThemeExpanded: Boolean) {
        _state.update { state ->
            state.copy(
                isThemeExpanded = isThemeExpanded,
                error = null,
                status = if (state.status == Status.AUTH_ERROR) Status.NEED_AUTH else state.status
            )
        }
    }

    private fun setTheme(theme: Theme) {
        onThemeExpandChange(false)

        viewModelScope.launch {
            setThemeUseCase(theme)
        }
    }

    init {
        viewModelScope.launch {
            if (isAuthorizedUseCase().not()) _state.update { state ->
                state.copy(status = Status.NEED_AUTH, error = null)
            }
        }

        viewModelScope.launch {
            getThemeUseCase().collectLatest { theme ->
                _state.update { state ->
                    state.copy(
                        theme = theme, error = null,
                        status = if (state.status == Status.AUTH_ERROR) Status.NEED_AUTH else state.status
                    )
                }
            }
        }

        viewModelScope.launch {
            getIsDynamicColorUseCase().collectLatest { isDynamic ->
                _state.update { state ->
                    state.copy(
                        isDynamicColor = isDynamic, error = null,
                        status = if (state.status == Status.AUTH_ERROR) Status.NEED_AUTH else state.status
                    )
                }
            }
        }

        viewModelScope.launch {
            intentKeeper.observe().collectLatest { intent ->
                intent.data?.let { uri ->
                    _state.update { state -> state.copy(status = Status.LOADING, error = null) }

                    authUserUseCase(uri).onFailure { error ->
                        _state.update { state ->
                            state.copy(
                                status = Status.AUTH_ERROR,
                                error = error.toErrorString(),
                            )
                        }
                    }.onSuccess { entity ->
                        _state.update { state ->
                            state.copy(
                                status = Status.COMPLETE,
                                user = entity.toModel(),
                                error = null,
                                isAuthorized = true
                            )
                        }

                    }
                } ?: _state.update { state ->
                    state.copy(
                        status = Status.AUTH_ERROR,
                        error = "Retry again for getting token",
                    )
                }
            }
        }

        viewModelScope.launch {
            getLastUsedAccountsUseCase().collectLatest { entities ->
                _state.update { state ->
                    state.copy(
                        otherAccounts = entities.map(UserEntity::toModel).toImmutableList()
                    )
                }
            }
        }

        viewModelScope.launch {
            getLastUsedAccountUseCase().collectLatest { entity ->
                _state.update { state ->
                    state.copy(user = entity.toModel(), status = Status.COMPLETE)
                }
            }
        }
    }
}
