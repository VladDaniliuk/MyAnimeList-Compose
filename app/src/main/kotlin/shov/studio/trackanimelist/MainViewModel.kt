package shov.studio.trackanimelist

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import core.mvi.keepers.IntentKeeper
import shov.studio.domain.auth.usecases.IsAuthorizedUseCase
import shov.studio.domain.auth.usecases.RefreshTokenUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val isAuthorizedUseCase: IsAuthorizedUseCase,
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val intentKeeper: IntentKeeper,
) : ViewModel() {
    fun refreshToken() {
        viewModelScope.launch {
            if (isAuthorizedUseCase()) refreshTokenUseCase()
        }
    }

    fun onNewIntent(intent: Intent) {
        intentKeeper.process(intent)
    }
}
