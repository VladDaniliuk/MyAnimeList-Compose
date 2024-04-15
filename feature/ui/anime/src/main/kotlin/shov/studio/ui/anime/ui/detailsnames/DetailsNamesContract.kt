package shov.studio.ui.anime.ui.detailsnames

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.persistentListOf
import core.mvi.UnidirectionalViewModel
import shov.studio.ui.anime.data.models.AlternativeTitlesModel
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesContract.Effect
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesContract.Event
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesContract.State

internal interface DetailsNamesContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(
        val names: AlternativeTitlesModel = AlternativeTitlesModel("", "", "", persistentListOf()),
        val status: Status = Status.LOADING, val error: String? = null,
    )

    enum class Status {
        LOADING, COMPLETE, ERROR
    }

    @Stable
    sealed interface Event {
        data object OnRetryClick : Event
    }

    sealed interface Effect
}
