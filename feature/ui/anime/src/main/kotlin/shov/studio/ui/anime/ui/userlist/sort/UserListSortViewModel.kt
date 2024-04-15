package shov.studio.ui.anime.ui.userlist.sort

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
import shov.studio.enums.Sort
import shov.studio.trackanimelist.navigator.AnimeRoute
import javax.inject.Inject

@HiltViewModel
internal class UserListSortViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel(), UserListSortContract {
    private val _state = MutableStateFlow(
        UserListSortContract.State(
            requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
            Sort.valueOf(requireNotNull(savedStateHandle[AnimeRoute.Args.sort]))
        )
    )
    override val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<UserListSortContract.Effect>()
    override val effect = _effect.asSharedFlow()

    fun effect(effect: UserListSortContract.Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: UserListSortContract.Event) {
        when (event) {
            UserListSortContract.Event.OnCancelClick -> effect(UserListSortContract.Effect.OnCancel)
            UserListSortContract.Event.OnSaveClick -> effect(
                UserListSortContract.Effect.OnSave(state.value.sort)
            )

            is UserListSortContract.Event.OnSortClick -> _state.update { state ->
                state.copy(sort = event.sort)
            }
        }
    }
}
