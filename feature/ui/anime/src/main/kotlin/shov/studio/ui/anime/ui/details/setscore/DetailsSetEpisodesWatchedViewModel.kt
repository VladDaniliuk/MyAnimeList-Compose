package shov.studio.ui.anime.ui.details.setscore

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import shov.studio.domain.core.usecases.UpdateNumEpisodesWatchedUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import javax.inject.Inject

@HiltViewModel
internal class DetailsSetEpisodesWatchedViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateNumEpisodesWatchedUseCase: UpdateNumEpisodesWatchedUseCase,
) : BaseChaptersVolumesViewModel(savedStateHandle) {
    override suspend fun updateValue(
        onSuccess: (Int) -> Unit, onError: (error: String) -> Unit,
    ) {
        updateNumEpisodesWatchedUseCase(
            requireNotNull(savedStateHandle[AnimeRoute.Args.id]), state.value.value.toInt()
        ).onSuccess(onSuccess).onFailure { throwable ->
            onError(throwable.localizedMessage ?: throwable.message ?: throwable.toString())
        }
    }
}
