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
import shov.studio.domain.auth.usecases.IsAuthorizedFlowUseCase
import shov.studio.domain.core.data.models.NodeStandardEntity
import shov.studio.domain.core.usecases.GetSearchResultUseCase
import core.utils.mapResponse
import shov.studio.domain.manga.usecases.GetMangaRankingAnimeUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.data.converters.toDetailsStandardModel
import shov.studio.ui.anime.ui.main.StandardListContract.Effect
import shov.studio.ui.anime.ui.main.StandardListContract.State
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
internal class MangaViewModel @Inject constructor(
    getMangaRankingAnimeUseCase: GetMangaRankingAnimeUseCase,
    private val getSearchResultUseCase: GetSearchResultUseCase,
    isAuthorizedFlowUseCase: IsAuthorizedFlowUseCase,
) : ViewModel(), StandardListContract {
    private fun onQueryChange(query: String = "") {
        _state.update { state -> state.copy(query = query) }
    }

    private val _state = MutableStateFlow(
        State(
            AnimeRoute.Type.manga,
            getMangaRankingAnimeUseCase(AnimeRoute.Type.manga).map { flow ->
                flow.mapResponse(NodeStandardEntity::toDetailsStandardModel)
                    .cachedIn(viewModelScope)
            }.toImmutableList()
        )
    )
    override val state: StateFlow<State> = _state.asStateFlow()

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val searchList = _state.debounce(200.milliseconds).flatMapMerge { state ->
        getSearchResultUseCase(AnimeRoute.Type.manga, state.query)
    }.mapResponse(NodeStandardEntity::toDetailsStandardModel)

    private val _effect = MutableSharedFlow<Effect>()
    override val effect = _effect.asSharedFlow()

    fun effect(effect: Effect) {
        viewModelScope.launch { _effect.emit(effect) }
    }

    override fun event(event: StandardListContract.Event) {
        when (event) {
            StandardListContract.Event.OnFilterClick -> effect(Effect.OnFilterOpen)
            is StandardListContract.Event.OnItemClick -> effect(
                Effect.OnItemOpen(
                    event.id
                )
            )

            is StandardListContract.Event.OnQueryChange -> onQueryChange(event.query)
            StandardListContract.Event.OnSettingsClick -> effect(Effect.OnSettingsOpen)
            is StandardListContract.Event.OnSearchBarClick -> _state.update { state ->
                state.copy(isSearchBarActive = event.isOpened)
            }

            is StandardListContract.Event.OnAddToListClick -> effect(Effect.OnFilterOpen)
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
