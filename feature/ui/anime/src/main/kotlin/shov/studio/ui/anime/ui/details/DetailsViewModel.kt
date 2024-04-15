package shov.studio.ui.anime.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.enums.Status
import core.mvi.keepers.NetworkKeeper
import core.navigator.utils.getAndRemove
import shov.studio.domain.auth.usecases.IsAuthorizedUseCase
import shov.studio.domain.core.usecases.GetDetailsUseCase
import shov.studio.domain.core.usecases.UpdateCommentsUseCase
import shov.studio.domain.core.usecases.UpdateIsRewatchingUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toDetailsModel
import shov.studio.ui.anime.data.models.ListStatusModel
import shov.studio.ui.anime.data.models.copy
import shov.studio.ui.anime.data.models.toModel
import shov.studio.ui.anime.ui.details.DetailsContract.Effect
import shov.studio.ui.anime.ui.details.DetailsContract.Event
import shov.studio.ui.anime.ui.details.DetailsContract.State
import shov.studio.ui.anime.ui.details.DetailsContract.Status.COMPLETE
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class DetailsViewModel @Inject constructor(
    private val getDetailsUseCase: GetDetailsUseCase,
    savedStateHandle: SavedStateHandle,
    private val updateCommentsUseCase: UpdateCommentsUseCase,
    private val updateIsRewatchingUseCase: UpdateIsRewatchingUseCase,
    private val isAuthorizedUseCase: IsAuthorizedUseCase,
    private val networkKeeper: NetworkKeeper,
) : ViewModel(), DetailsContract {
    val type: String = checkNotNull(savedStateHandle[AnimeRoute.Args.type])
    val id: Int = checkNotNull(savedStateHandle[AnimeRoute.Args.id])

    private val _state = MutableStateFlow(State(type = type))
    override val state = _state.asStateFlow()

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    init {
        viewModelScope.launch {
            networkKeeper.observe().collectLatest { isConnected ->
                if (isConnected and (DetailsContract.Status.EMPTY_ERROR == state.value.status))
                    loadDetails()
            }
        }

        if (state.value.status != COMPLETE) loadDetails()

        viewModelScope.launch {
            _state.update { state -> state.copy(isAuthorized = isAuthorizedUseCase()) }
        }
    }

    fun closeError() {
        _state.update { state -> state.closeError() }
    }

    private fun refreshDetails() {
        _state.update { state -> state.refresh() }

        getDetails()
    }

    private fun getDetails() {
        viewModelScope.launch {
            getDetailsUseCase(type, id).onSuccess { entity ->
                _state.update { state -> state.complete(entity.toDetailsModel(state.isAnime)) }

                viewModelScope.launch { _effect.emit(Effect.OnRefreshStop) }
            }.onFailure { error ->
                viewModelScope.launch {
                    _effect.emit(Effect.OnErrorShow(error.toErrorString(), ::getDetails))
                }

                _state.update { state -> state.error(error.toErrorString(), ::getDetails) }

                viewModelScope.launch { _effect.emit(Effect.OnRefreshStop) }
            }
        }
    }

    private fun loadDetails() {
        _state.update { state -> state.loading() }

        getDetails()
    }

    private fun deleteComments() {
        viewModelScope.launch {
            updateCommentsUseCase(type, id, "").onSuccess {
                _state.update { state ->
                    state.details(comments = "").copy(isCommentEditable = true)
                }
            }.onFailure { error ->
                _state.update { state -> state.error(error.toErrorString(), ::deleteComments) }
            }
        }
    }

    private fun updateComments() {
        viewModelScope.launch {
            updateCommentsUseCase(type, id, state.value.details.listStatus.comments)
                .onSuccess { entity ->
                    _state.update { state ->
                        state.copy(
                            details = state.details
                                .copy(listStatus = entity.toModel(state.isAnime)),
                            isCommentEditable = false
                        )
                    }
                }.onFailure { error ->
                    viewModelScope.launch {
                        _effect.emit(Effect.OnErrorShow(error.toErrorString(), ::updateComments))
                    }
                }
        }
    }

    private fun updateIsRewatching(isRewatching: Boolean) {
        _state.update { state -> state.details(isRewatching = isRewatching) }

        viewModelScope.launch {
            updateIsRewatchingUseCase(type, id, isRewatching).onFailure { error ->
                _state.update { state ->
                    state.details(isRewatching = isRewatching.not())
                        .error(error.toErrorString()) { updateIsRewatching(isRewatching) }
                }
            }
        }
    }

    private fun updateStatus(status: Status) {
        updateListStatusModel(
            if (status == Status.delete) ListStatusModel(isAnime = state.value.isAnime)
            else state.value.details.listStatus.copy(isAnime = state.value.isAnime, status = status)
        )
    }

    private fun updateScore(score: Int) {
        updateListStatusModel(
            state.value.details.listStatus.copy(
                isAnime = state.value.isAnime,
                score = score
            )
        )
    }

    private fun updateEpisodes(episodes: Int) {
        updateListStatusModel(
            state.value.details.listStatus.copy(
                isAnime = state.value.isAnime,
                numEpisodesWatched = episodes
            )
        )
    }

    private fun updateVolumes(volumes: Int) {
        updateListStatusModel(
            state.value.details.listStatus.copy(
                isAnime = state.value.isAnime,
                numVolumesRead = volumes
            )
        )
    }

    private fun updateChapters(chapters: Int) {
        updateListStatusModel(
            state.value.details.listStatus.copy(
                isAnime = state.value.isAnime,
                numChaptersRead = chapters
            )
        )
    }

    private fun updateListStatusModel(status: ListStatusModel = ListStatusModel(isAnime = state.value.isAnime)) {
        _state.update { state ->
            state.copy(details = state.details.copy(listStatus = status))
        }
    }

    private fun deleteTag(deletedTag: String) {
        _state.update { state ->
            state.copy(
                details = state.details.copy(
                    listStatus = state.details.listStatus.copy(
                        tags = state.details.listStatus.tags.minus(deletedTag)
                    )
                )
            )
        }
    }

    fun onSavedStateChanged(savedStateHandle: SavedStateHandle) {
        savedStateHandle.getAndRemove(AnimeRoute.Args.deletedTag, ::deleteTag)
        savedStateHandle.getAndRemove(AnimeRoute.Args.addedTag, ::addTag)
        savedStateHandle.getAndRemove(AnimeRoute.Args.status, ::updateStatus)
        savedStateHandle.getAndRemove(AnimeRoute.Args.score, ::updateScore)
        savedStateHandle.getAndRemove(AnimeRoute.Args.episodes, ::updateEpisodes)
        savedStateHandle.getAndRemove(AnimeRoute.Args.volumes, ::updateVolumes)
        savedStateHandle.getAndRemove(AnimeRoute.Args.chapters, ::updateChapters)
    }

    private fun addTag(addedTag: String) {
        _state.update { state ->
            state.copy(
                details = state.details.copy(
                    listStatus = state.details.listStatus.copy(
                        tags = state.details.listStatus.tags.plus(addedTag.split(','))
                    )
                )
            )
        }
    }

    override fun event(event: Event) {
        when (event) {
            Event.OnBackClick -> viewModelScope.launch { _effect.emit(Effect.OnBack) }
            is Event.OnIconClick -> viewModelScope.launch {
                _effect.emit(Effect.OnIconOpen(type, id, event.index))
            }

            Event.OnNameClick -> viewModelScope.launch {
                _effect.emit(Effect.OnNameOpen(type, id))
            }

            Event.OnAddToListClick -> viewModelScope.launch {
                _effect.emit(
                    Effect.OnAddToList(
                        type,
                        id,
                        state.value.details.listStatus.status
                    )
                )
            }

            Event.OnTagAddClick -> viewModelScope.launch {
                _effect.emit(
                    Effect.OnTagAdd(
                        type, id, state.value.details.listStatus.tags
                    )
                )
            }

            is Event.OnTagDeleteClick -> viewModelScope.launch {
                _effect.emit(
                    Effect.OnTagDelete(
                        type, id, event.tag, state.value.details.listStatus.tags
                    )
                )
            }

            is Event.OnAnimeClick -> viewModelScope.launch {
                _effect.emit(Effect.OnAnimeOpen(event.id))
            }

            Event.OnCommentDeleteClick -> deleteComments()
            is Event.OnCommentSetClick -> updateComments()
            is Event.OnDetailsClick -> viewModelScope.launch {
                _effect.emit(
                    if (type == AnimeRoute.Type.anime) Effect.OnAnimeOpen(event.id)
                    else Effect.OnMangaOpen(event.id)
                )
            }

            is Event.OnMangaClick -> viewModelScope.launch {
                _effect.emit(Effect.OnMangaOpen(event.id))
            }

            Event.OnRefreshClick -> refreshDetails()
            is Event.OnRelatedClick -> viewModelScope.launch {
                _effect.emit(Effect.OnRelatedOpen(event.type))
            }

            is Event.OnRewathcingClick -> updateIsRewatching(event.isRewatching)
            Event.OnSetScoreClick -> viewModelScope.launch {
                _effect.emit(
                    Effect.OnSetScoreOpen(type, id, state.value.details.listStatus.score)
                )
            }

            Event.OnErrorClick -> loadDetails()
            Event.OnChaptersReadClick ->
                state.value.details.numChapters?.let { num ->
                    viewModelScope.launch {
                        _effect.emit(
                            Effect.OnChaptersRead(type, id, state.value.details.status, num)
                        )
                    }
                }

            Event.OnVolumesReadClick -> state.value.details.numVolumes?.let { num ->
                viewModelScope.launch {
                    _effect.emit(Effect.OnVolumesRead(type, id, state.value.details.status, num))
                }
            }

            Event.OnEpisodesWatchedClick -> state.value.details.numEpisodes?.let { num ->
                viewModelScope.launch {
                    _effect.emit(
                        Effect.OnEpisodesWatched(type, id, state.value.details.status, num)
                    )
                }
            }

            is Event.OnCommentChange -> {
                _state.update { state ->
                    state.copy(
                        details = state.details.copy(
                            listStatus = state.details.listStatus.copy(comments = event.comment)
                        )
                    )
                }
            }

            Event.OnCommentChangeClick -> _state.update { state -> state.copy(isCommentEditable = true) }
            Event.OnCommentMenuCloseClick -> _state.update { state -> state.copy(isCommentMenuOpened = false) }
            Event.OnCommentMenuOpenClick -> _state.update { state -> state.copy(isCommentMenuOpened = true) }
        }
    }
}
