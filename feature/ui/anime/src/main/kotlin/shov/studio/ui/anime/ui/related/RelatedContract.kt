package shov.studio.ui.anime.ui.related

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.data.models.DetailsStandardModel
import shov.studio.ui.anime.ui.related.RelatedContract.Effect
import shov.studio.ui.anime.ui.related.RelatedContract.Event
import shov.studio.ui.anime.ui.related.RelatedContract.State
import core.ui.error.toErrorString

internal interface RelatedContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val type: String,
        val related: ImmutableList<DetailsStandardModel> = (1..10).map { id ->
            DetailsStandardModel(id, "", null, null, "")
        }.toImmutableList(),
        val status: Status = Status.LOADING,
        val error: String? = null,
        val snackbarHostState: SnackbarHostState = SnackbarHostState(),
        val isAuthorized: Boolean = false,
    ) {
        fun loading() = copy(related = (1..10).map { id ->
            DetailsStandardModel(id, "", null, null, "")
        }.toImmutableList(), status = Status.LOADING, error = null)

        fun error(error: Throwable) = copy(
            related = if (status == Status.LOADING) persistentListOf() else related,
            status = Status.ERROR,
            error = error.toErrorString()
        )

        val isErrorVisible = (status == Status.ERROR) and related.isEmpty()
    }

    enum class Status {
        LOADING, COMPLETE, ERROR, REFRESH
    }

    @Stable
    sealed interface Event {
        data object OnRefreshClick : Event
        data object OnRetryClick : Event
        data class OnItemClick(val id: Int) : Event
        data object OnBackClick : Event
        data class OnAddToListClick(val id: Int, val type: String) : Event
    }

    sealed interface Effect {
        data class OnItemOpen(val id: Int, val type: String) : Effect
        data object OnBack : Effect
        data object OnComplete : Effect
        data class OnError(val error: String) : Effect
        data class OnAddToList(val id: Int, val type: String) : Effect
    }
}
