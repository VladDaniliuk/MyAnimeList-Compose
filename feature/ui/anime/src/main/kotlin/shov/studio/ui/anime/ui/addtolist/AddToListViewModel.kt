package shov.studio.ui.anime.ui.addtolist

import androidx.lifecycle.SavedStateHandle
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
import shov.studio.domain.core.usecases.GetDetailsUseCase
import shov.studio.domain.core.usecases.UpdateDetailsUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toDetailsModel
import shov.studio.ui.anime.ui.addtolist.AddToListContract.Event
import shov.studio.ui.anime.ui.addtolist.AddToListContract.State
import shov.studio.ui.anime.ui.addtolist.AddToListContract.Status
import core.ui.error.toErrorString
import javax.inject.Inject
import shov.studio.enums.Status as StatusType

@HiltViewModel
class AddToListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getDetailsUseCase: GetDetailsUseCase,
    private val updateDetailsUseCase: UpdateDetailsUseCase,
) : ViewModel(), AddToListContract {
    private val _state =
        MutableStateFlow(State(requireNotNull(savedStateHandle[AnimeRoute.Args.type]) == AnimeRoute.Type.anime))
    override val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<AddToListContract.Effect>()
    override val effect = _effect.asSharedFlow()

    private fun effect(effect: AddToListContract.Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: Event) {
        when (event) {
            Event.OnCancelClick -> effect(AddToListContract.Effect.OnCancel)
            is Event.OnChaptersChange -> {
                val chapters =
                    event.chapters.toIntOrNull() ?: if (event.chapters.isEmpty()) 0 else return

                if ((chapters <= (state.value.maxChapters
                        ?: Int.MAX_VALUE)) or (state.value.maxChapters == 0)
                ) {
                    _state.update { state -> state.copy(chapters = chapters) }
                }
            }

            is Event.OnCommentChange -> _state.update { state ->
                state.copy(comment = event.comment)
            }

            Event.OnConfirmClick -> {
                _state.update { state -> state.copy(status = Status.UPDATE) }

                viewModelScope.launch {
                    updateDetailsUseCase(
                        type = requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
                        id = requireNotNull(savedStateHandle[AnimeRoute.Args.id]).toString()
                            .toInt(),
                        status = state.value.userStatus
                            ?: if (state.value.isAnime) StatusType.watching else StatusType.reading,
                        score = state.value.score.toInt(),
                        tags = state.value.tags.toList(),
                        comment = state.value.comment,
                        isRewatching = state.value.isRewatching,
                        numChaptersRead = state.value.chapters,
                        numVolumesRead = state.value.volumes,
                        numWatchedEpisodes = state.value.episodes
                    ).onSuccess {
                        effect(AddToListContract.Effect.OnComplete)
                    }.onFailure {
                        _state.update { state ->
                            state.copy(status = Status.UPDATE_ERROR, error = it.toErrorString())
                        }
                    }
                }
            }

            is Event.OnEpisodesChange -> {
                val episodes =
                    event.episodes.toIntOrNull() ?: if (event.episodes.isEmpty()) 0 else return

                if ((episodes <= (state.value.maxEpisodes
                        ?: Int.MAX_VALUE)) or (state.value.maxEpisodes == 0)
                ) {
                    _state.update { state -> state.copy(episodes = episodes) }
                }
            }

            Event.OnRetryClick -> {
                _state.update { state ->
                    state.copy(status = Status.LOADING, error = null)
                }

                viewModelScope.launch {
                    getDetailsUseCase(
                        requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
                        requireNotNull(savedStateHandle[AnimeRoute.Args.id]).toString().toInt()
                    ).onSuccess { entity ->
                        val model = entity.toDetailsModel(state.value.isAnime)

                        _state.update { state ->
                            state.copy(
                                tags = model.listStatus.tags.toImmutableList(),
                                status = Status.COMPLETE,
                                userStatus = model.listStatus.status,
                                isRewatching = model.listStatus.isRewatching,
                                score = model.listStatus.score.toFloat(),
                                episodes = model.listStatus.numEpisodesWatched,
                                maxEpisodes = model.numEpisodes,
                                chapters = model.listStatus.numChaptersRead,
                                maxChapters = model.numChapters,
                                volumes = model.listStatus.numVolumesRead,
                                maxVolumes = model.numVolumes,
                                detailsStatus = model.status,
                                error = null
                            )
                        }
                    }.onFailure { error ->
                        _state.update { state ->
                            state.copy(status = Status.ERROR, error = error.toErrorString())
                        }
                    }
                }
            }

            is Event.OnRewatchingClick -> _state.update { state ->
                state.copy(isRewatching = event.isRewatching)
            }

            is Event.OnScoreChange -> _state.update { state -> state.copy(score = event.score) }
            is Event.OnStatusClick -> _state.update { state ->
                state.copy(userStatus = event.status, isStatusMenuOpen = false)
            }

            Event.OnTagAddClick -> _state.update { state -> state.copy(isAddTagOpen = true) }

            is Event.OnTagChange -> _state.update { state -> state.copy(tag = event.tag) }
            is Event.OnTagDeleteClick -> _state.update { state ->
                state.copy(
                    tags = state.tags.toMutableList().let { list ->
                        list.removeAt(event.index)
                        list.toImmutableList()
                    }
                )
            }

            Event.OnTagSaveClick -> _state.update { state ->
                state.copy(
                    tags = state.tags.toMutableList().let { list ->
                        list.add(state.tag)
                        list.toImmutableList()
                    },
                    tag = "",
                    isAddTagOpen = false
                )
            }

            is Event.OnVolumesChange -> {
                val volumes =
                    event.volumes.toIntOrNull() ?: if (event.volumes.isEmpty()) 0 else return

                if ((volumes <= (state.value.maxVolumes
                        ?: Int.MAX_VALUE)) or (state.value.maxVolumes == 0)
                ) {
                    _state.update { state -> state.copy(volumes = volumes) }
                }
            }

            is Event.OnStatusOpenChange -> _state.update { state ->
                state.copy(isStatusMenuOpen = event.isStatusMenuOpen)
            }
        }
    }

    init {
        viewModelScope.launch {
            getDetailsUseCase(
                requireNotNull(savedStateHandle[AnimeRoute.Args.type]),
                requireNotNull(savedStateHandle[AnimeRoute.Args.id]).toString().toInt()
            ).onSuccess { entity ->
                val model = entity.toDetailsModel(state.value.isAnime)

                _state.update { state ->
                    state.copy(
                        tags = model.listStatus.tags.toImmutableList(),
                        status = Status.COMPLETE,
                        userStatus = model.listStatus.status,
                        isRewatching = model.listStatus.isRewatching,
                        score = model.listStatus.score.toFloat(),
                        episodes = model.listStatus.numEpisodesWatched,
                        maxEpisodes = model.numEpisodes,
                        chapters = model.listStatus.numChaptersRead,
                        maxChapters = model.numChapters,
                        volumes = model.listStatus.numVolumesRead,
                        maxVolumes = model.numVolumes,
                        detailsStatus = model.status
                    )
                }
            }.onFailure { error ->
                _state.update { state ->
                    state.copy(status = Status.ERROR, error = error.toErrorString())
                }
            }
        }
    }
}
