package shov.studio.ui.user.ui.accauntmanagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shov.studio.domain.auth.usecases.DeleteAccountUseCase
import shov.studio.domain.auth.usecases.GetAccountsUseCase
import shov.studio.domain.auth.usecases.OnUserChangeUseCase
import shov.studio.feature.ui.models.data.converters.toModel
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract.Effect
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract.State
import core.utils.mapResponse
import javax.inject.Inject

@HiltViewModel
internal class AccountManagementViewModel @Inject constructor(
    getAccountsUseCase: GetAccountsUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase,
    private val onUserChangeUseCase: OnUserChangeUseCase,
) : ViewModel(), AccountManagementContract {
    private val _state = MutableStateFlow(State(getAccountsUseCase().mapResponse { toModel() }))
    private val _effect = MutableSharedFlow<Effect>()
    override val state = _state.asStateFlow()
    override val effect = _effect.asSharedFlow()

    private fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: AccountManagementContract.Event) {
        when (event) {
            is AccountManagementContract.Event.OnDeleteClick -> viewModelScope.launch {
                deleteAccountUseCase(event.id)
            }

            is AccountManagementContract.Event.OnDefaultClick -> viewModelScope.launch {
                onUserChangeUseCase(event.id)
            }

            AccountManagementContract.Event.OnBackClick -> effect(Effect.OnBack)
        }
    }
}
