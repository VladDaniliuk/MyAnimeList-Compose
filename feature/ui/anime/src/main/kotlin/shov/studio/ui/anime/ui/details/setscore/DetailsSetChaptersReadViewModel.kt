package shov.studio.ui.anime.ui.details.setscore

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import shov.studio.domain.core.usecases.UpdateNumChaptersReadUseCase
import shov.studio.trackanimelist.navigator.AnimeRoute
import core.ui.error.toErrorString
import javax.inject.Inject

@HiltViewModel
internal class DetailsSetChaptersReadViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updateNumChaptersReadUseCase: UpdateNumChaptersReadUseCase,
) : BaseChaptersVolumesViewModel(savedStateHandle) {
    override suspend fun updateValue(
        onSuccess: (Int) -> Unit, onError: (error: String) -> Unit,
    ) {
        updateNumChaptersReadUseCase(
            requireNotNull(savedStateHandle[AnimeRoute.Args.id]), state.value.value.toInt()
        ).onSuccess(onSuccess).onFailure { error -> onError(error.toErrorString()) }
    }
}
