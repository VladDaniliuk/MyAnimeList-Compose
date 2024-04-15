package shov.studio.ui.anime.ui.details.setscore

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.enums.DetailsStatus
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R

internal abstract class BaseChaptersVolumesViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel(), DetailsSetChaptersReadContract {
    private val _state = MutableStateFlow(
        DetailsSetChaptersReadContract.State(
            R.string.chapters_read,
            requireNotNull(savedStateHandle.get<Int>(AnimeRoute.Args.max))
        )
    )
    override val state: StateFlow<DetailsSetChaptersReadContract.State>
        get() = _state.asStateFlow()

    private val _effect = MutableSharedFlow<DetailsSetChaptersReadContract.Effect>()
    override val effect: SharedFlow<DetailsSetChaptersReadContract.Effect>
        get() = _effect.asSharedFlow()

    abstract suspend fun updateValue(
        onSuccess: (Int) -> Unit,
        onError: (error: String) -> Unit,
    )

    override fun event(event: DetailsSetChaptersReadContract.Event) {
        when (event) {
            DetailsSetChaptersReadContract.Event.OnCancelClick -> viewModelScope.launch {
                _effect.emit(DetailsSetChaptersReadContract.Effect.OnCancel)
            }

            DetailsSetChaptersReadContract.Event.OnSaveClick -> {
                _state.update { state -> state.loading() }

                viewModelScope.launch {
                    updateValue({ value ->
                        viewModelScope.launch {
                            _effect.emit(DetailsSetChaptersReadContract.Effect.OnClose(value))
                        }
                    }, { error ->
                        _state.update { state -> state.onError(error) }
                    })
                }
            }

            is DetailsSetChaptersReadContract.Event.OnValueChange -> {
                val reg = Regex("\\d+")
                if (((state.value.max == 0) and
                            (requireNotNull(savedStateHandle.get<DetailsStatus>(AnimeRoute.Args.status)) != DetailsStatus.not_yet_aired) and
                            (requireNotNull(savedStateHandle.get<DetailsStatus>(AnimeRoute.Args.status)) != DetailsStatus.not_yet_published)
                        )
                    or (event.value.matches(reg) and ((event.value.toIntOrNull() ?: 0) <= state.value.max))
                    or event.value.isEmpty()
                ) _state.update { state -> state.onValueSet(event.value) }
            }
        }
    }
}
