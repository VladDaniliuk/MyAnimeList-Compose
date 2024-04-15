package shov.studio.ui.anime.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import shov.studio.domain.anime.usecases.GetAnimeRankingAnimeUseCase
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import shov.studio.domain.core.data.models.NodeStandardEntity
import shov.studio.domain.core.usecases.GetSearchResultUseCase
import core.utils.mapResponse
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toDetailsStandardModel
import shov.studio.ui.anime.ui.main.StandardListContract.Effect
import shov.studio.ui.anime.ui.main.StandardListContract.Event
import shov.studio.ui.anime.ui.main.StandardListContract.State
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
internal class AnimeViewModel @Inject constructor(
    getAnimeRankingAnimeUseCase: GetAnimeRankingAnimeUseCase,
    private val getSearchResultUseCase: GetSearchResultUseCase,
    private val isAuthorizedFlowUseCase: IsAuthorizedFlowUseCase,
) : ViewModel(), StandardListContract {
    private fun onQueryChange(query: String = "") {
        _state.update { state -> state.copy(query = query) }
    }

    private val _state = MutableStateFlow(
        State(
            AnimeRoute.Type.anime,
            getAnimeRankingAnimeUseCase(AnimeRoute.Type.anime).map { flow ->
                flow.mapResponse(NodeStandardEntity::toDetailsStandardModel)
                    .cachedIn(viewModelScope)
            }.toImmutableList()
        )
    )
    override val state: StateFlow<State> = _state.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchList = _state.debounce(200.milliseconds).flatMapMerge { state ->
        getSearchResultUseCase("anime", state.query)
    }.mapResponse(NodeStandardEntity::toDetailsStandardModel)


    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: Event) {
        when (event) {
            Event.OnFilterClick -> effect(Effect.OnFilterOpen)
            is Event.OnItemClick -> effect(Effect.OnItemOpen(event.id))
            is Event.OnQueryChange -> onQueryChange(event.query)
            Event.OnSettingsClick -> effect(Effect.OnSettingsOpen)
            is Event.OnSearchBarClick -> _state.update { state ->
                state.copy(isSearchBarActive = event.isOpened)
            }

            is Event.OnAddToListClick -> effect(Effect.OnAddToList(event.id, event.type))
        }
    }

    init {
        viewModelScope.launch {
            isAuthorizedFlowUseCase().collectLatest { isAuthorized ->
                _state.update { state -> state.copy(isAuthorized = isAuthorized) }
            }
        }
    }
}
