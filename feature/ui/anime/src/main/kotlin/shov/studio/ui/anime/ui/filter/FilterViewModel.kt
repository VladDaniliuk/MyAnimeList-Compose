package shov.studio.ui.anime.ui.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.enums.Season
import shov.studio.enums.SortType
import javax.inject.Inject

@HiltViewModel
internal class FilterViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(FilterUiState())
    val uiState = _uiState.stateIn(viewModelScope, SharingStarted.Lazily, FilterUiState())

    fun onYearChanged(year: String) {
        _uiState.update { uiState ->
            uiState.copy(year = year)
        }
    }

    fun onSeasonSelected(selectedSeason: Season) {
        _uiState.update { uiState ->
            uiState.copy(isSeasonExpanded = false, season = selectedSeason)
        }
    }

    fun onSeasonExpanded(isSeasonExpanded: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(isSeasonExpanded = isSeasonExpanded)
        }
    }

    fun onSortSelected(selectedSort: SortType?) {
        _uiState.update { uiState ->
            uiState.copy(sort = selectedSort, isSortExpanded = false)
        }
    }

    fun onSortExpanded(isSortExpanded: Boolean) {
        _uiState.update { uiState ->
            uiState.copy(isSortExpanded = isSortExpanded)
        }
    }

    fun onYearSelected(onClear: () -> Unit) {
        if (uiState.value.season == null) {
            uiState.value.seasonFocusRequester.requestFocus()
            _uiState.update { uiState ->
                uiState.copy(isSeasonExpanded = true)
            }
        } else {
            onClear()
        }
    }

    fun onConfirm(
        yearError: String,
        seasonError: String,
        onSuccess: (year: String, season: Season, sort: SortType) -> Unit,
    ) {
        if (uiState.value.year.isEmpty()) {
            viewModelScope.launch {
                uiState.value.snackbarHostState.showSnackbar(yearError)
            }
        } else uiState.value.season?.let { season ->
            onSuccess(uiState.value.year, season, uiState.value.sort ?: SortType.anime_score)
        } ?: viewModelScope.launch {
            uiState.value.snackbarHostState.showSnackbar(seasonError)
        }
    }

    fun onReset() {
        _uiState.update { uiState ->
            uiState.copy(
                year = "",
                season = null,
                sort = null,
                isSeasonExpanded = false,
                isSortExpanded = false
            )
        }
    }
}
