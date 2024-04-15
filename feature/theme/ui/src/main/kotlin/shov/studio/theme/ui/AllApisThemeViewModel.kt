package shov.studio.theme.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import shov.studio.core.data.Theme
import shov.studio.domain.usecases.GetIsDynamicColorUseCase
import shov.studio.domain.usecases.GetThemeUseCase
import javax.inject.Inject

@HiltViewModel
class AllApisThemeViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase,
    getIsDynamicColorUseCase: GetIsDynamicColorUseCase,
) : ViewModel() {
    val theme =
        getThemeUseCase().map { theme ->
            when (theme) {
                Theme.DARK -> true
                Theme.LIGHT -> false
                Theme.DEFAULT -> null
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val isDynamicColor = getIsDynamicColorUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, true)
}
