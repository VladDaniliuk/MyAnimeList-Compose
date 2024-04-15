package shov.studio.ui.user.ui.user

import android.net.Uri
import androidx.compose.runtime.Immutable
import core.mvi.UnidirectionalViewModel
import core.ui.piechart.PieState
import shov.studio.feature.ui.models.data.models.AnimeStatisticsModel
import shov.studio.feature.ui.models.data.models.UserFullModel
import shov.studio.ui.user.ui.user.UserContract.Effect
import shov.studio.ui.user.ui.user.UserContract.Event
import shov.studio.ui.user.ui.user.UserContract.State

internal interface UserContract : UnidirectionalViewModel<State, Event, Effect> {
    @Immutable
    data class State(
        val status: Status = Status.LOADING,
        val user: UserFullModel = UserFullModel(
            100000000,
            "Long Long Name",
            "",
            "Long Long Date",
            "Long Long Location",
            AnimeStatisticsModel(
                100000f,
                100000f,
                100000f,
                100000f,
                100000f,
                100000f,
                100000f,
                100000,
                100000,
                100000,
                100000,
                100000,
                100000,
                100000,
                100000
            )
        ),
        val error: String? = null,
        val isMenuOpened: Boolean = false,
    ) {
        val pieStatus = if (status == Status.COMPLETE) PieState.Complete else PieState.Loading
        val isPlaceholderVisible = status == Status.LOADING
    }

    enum class Status {
        LOADING, NEED_AUTH, AUTH_ERROR, ERROR, COMPLETE, REFRESH
    }

    @Immutable
    sealed class Event {
        data object OnRefresh : Event()
        data object OnLoadClick : Event()
        data object OnLinkClick : Event()
        data object OnLogOutClick : Event()
        data object OnMenuOpenClick : Event()
        data object OnMenuCloseClick : Event()
    }

    sealed interface Effect {
        data class OnLinkLoaded(val link: Uri) : Effect
        data object OnRefreshFinish : Effect
        data class OnLogOut(val id: String) : Effect
    }
}
