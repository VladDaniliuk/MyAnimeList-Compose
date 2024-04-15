package shov.studio.ui.anime.ui.userlist.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.enums.Sort
import shov.studio.enums.Status
import shov.studio.domain.core.data.models.NodeStandardEntity
import shov.studio.domain.core.usecases.GetUserListPreviewUseCase
import shov.studio.domain.core.usecases.GetUserListsPreviewUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toDetailsStandardModel
import shov.studio.ui.anime.ui.userlist.main.UserListContract.Effect
import shov.studio.ui.anime.ui.userlist.main.UserListContract.Event
import shov.studio.ui.anime.ui.userlist.main.UserListContract.State
import core.ui.error.toErrorString
import javax.inject.Inject
import shov.studio.ui.anime.ui.userlist.main.UserListContract.Status as UserListStatus

@HiltViewModel
internal class UserListViewModel @Inject constructor(
    private val getUserListPreviewUseCase: GetUserListPreviewUseCase,
    private val getUserListsPreviewUseCase: GetUserListsPreviewUseCase,
) : ViewModel(), UserListContract {
    private val _state = MutableStateFlow(State())
    override val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: Event) {
        when (event) {
            is Event.OnItemClick -> effect(Effect.OnItemOpen(event.type, event.id))
            is Event.OnListClick -> effect(Effect.OnListOpen(event.type))
            is Event.OnLoadRetryClick -> {
                if ((state.value.anime.status == UserListStatus.ERROR)
                    and (state.value.anime.error != null)
                ) loadAnime()

                if ((state.value.manga.status == UserListStatus.ERROR)
                    and (state.value.manga.error != null)
                ) loadManga()
            }
        }
    }

    private fun loadAnime() {
        _state.update { state -> state.copy(anime = state.anime.loading()) }

        viewModelScope.launch {
            getUserListPreviewUseCase(AnimeRoute.Type.anime, Status.watching, Sort.list_updated_at)
                .map { animes ->
                    animes.map(NodeStandardEntity::toDetailsStandardModel).toImmutableList()
                }.onSuccess { animes ->
                    _state.update { state ->
                        state.copy(
                            anime = state.anime.copy(
                                details = animes, status = UserListStatus.COMPLETE
                            )
                        )
                    }
                }.onFailure { error ->
                    _state.update { state ->
                        state.copy(
                            anime = state.anime.copy(
                                status = UserListStatus.ERROR, error = error.toErrorString()
                            )
                        )
                    }
                }
        }
    }

    private fun loadManga() {
        _state.update { state -> state.copy(manga = state.manga.loading()) }

        viewModelScope.launch {
            getUserListPreviewUseCase(AnimeRoute.Type.manga, Status.reading, Sort.list_updated_at)
                .map { mangas ->
                    mangas.map(NodeStandardEntity::toDetailsStandardModel).toImmutableList()
                }.onSuccess { mangas ->
                    _state.update { state ->
                        state.copy(
                            manga = state.manga.copy(
                                details = mangas, status = UserListStatus.COMPLETE
                            )
                        )
                    }
                }.onFailure { error ->
                    _state.update { state ->
                        state.copy(
                            manga = state.manga.copy(
                                error = error.toErrorString(), status = UserListStatus.ERROR
                            )
                        )
                    }
                }
        }
    }

    init {
        viewModelScope.launch {
            val (anime, manga) = getUserListsPreviewUseCase()

            anime.onSuccess { entity ->
                _state.update { state ->
                    state.copy(
                        anime = state.anime.copy(
                            details = entity.map(NodeStandardEntity::toDetailsStandardModel)
                                .toImmutableList(),
                            status = UserListStatus.COMPLETE, error = null
                        )
                    )
                }
            }.onFailure { error ->
                _state.update { state ->
                    state.copy(
                        anime = state.anime.copy(
                            status = UserListStatus.ERROR, error = error.toErrorString()
                        )
                    )
                }
            }

            manga.onSuccess { entity ->
                _state.update { state ->
                    state.copy(
                        manga = state.manga.copy(
                            details = entity.map(NodeStandardEntity::toDetailsStandardModel)
                                .toImmutableList(),
                            status = UserListStatus.COMPLETE, error = null
                        )
                    )
                }
            }.onFailure { error ->
                _state.update { state ->
                    state.copy(
                        manga = state.manga.copy(
                            status = UserListStatus.ERROR, error = error.toErrorString()
                        )
                    )
                }
            }
        }
    }
}
