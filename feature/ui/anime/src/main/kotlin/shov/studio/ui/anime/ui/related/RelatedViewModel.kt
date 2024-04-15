package shov.studio.ui.anime.ui.related

import androidx.lifecycle.SavedStateHandle
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
import core.mvi.keepers.NetworkKeeper
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import shov.studio.domain.core.data.models.RelatedModelEntity
import shov.studio.domain.core.usecases.GetRelatedAnimeUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toDetailsStandardModels
import shov.studio.ui.anime.ui.related.RelatedContract.Effect
import shov.studio.ui.anime.ui.related.RelatedContract.Event
import shov.studio.ui.anime.ui.related.RelatedContract.State
import shov.studio.ui.anime.ui.related.RelatedContract.Status
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class RelatedViewModel @Inject constructor(
    private val getRelatedAnimeUseCase: GetRelatedAnimeUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val networkKeeper: NetworkKeeper,
    isAuthorizedFlowUseCase: IsAuthorizedFlowUseCase,
) : ViewModel(), RelatedContract {
    private val _state =
        MutableStateFlow(State(requireNotNull(savedStateHandle[AnimeRoute.Args.type])))
    override val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    private fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: Event) {
        when (event) {
            Event.OnBackClick -> effect(Effect.OnBack)
            is Event.OnItemClick -> effect(Effect.OnItemOpen(event.id, state.value.type))
            Event.OnRefreshClick -> {
                _state.update { state -> state.copy(status = Status.REFRESH, error = null) }

                getRelatedAnime()
            }

            Event.OnRetryClick -> {
                _state.update(State::loading)

                getRelatedAnime()
            }

            is Event.OnAddToListClick -> effect(Effect.OnAddToList(event.id, event.type))
        }
    }

    private fun getRelatedAnime() {
        viewModelScope.launch {
            getRelatedAnimeUseCase(
                requireNotNull(savedStateHandle[AnimeRoute.Args.id]),
                requireNotNull(savedStateHandle[AnimeRoute.Args.type])
            ).onSuccess { entities ->
                _state.update { state ->
                    state.copy(
                        related = entities.map(RelatedModelEntity::toDetailsStandardModels)
                            .toImmutableList(),
                        status = Status.COMPLETE, error = null
                    )
                }
            }.onFailure { error ->
                _state.update { state -> state.error(error) }

                if (state.value.related.isNotEmpty()) effect(Effect.OnError(error.toErrorString()))
            }
        }.invokeOnCompletion {
            effect(Effect.OnComplete)
        }
    }

    init {
        getRelatedAnime()

        viewModelScope.launch {
            networkKeeper.observe().collectLatest { isConnected ->
                if (isConnected) {
                    if ((state.value.status == Status.ERROR) and (state.value.error != null)) {
                        _state.update(State::loading)

                        getRelatedAnime()
                    }
                }
            }
        }

        viewModelScope.launch {
            isAuthorizedFlowUseCase().collectLatest { isAuthorized ->
                _state.update { state -> state.copy(isAuthorized = isAuthorized) }
            }
        }
    }
}
