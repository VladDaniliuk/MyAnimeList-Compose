package shov.studio.ui.anime.navigation

import androidx.compose.runtime.Stable
import core.mvi.UnidirectionalViewModel
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.navigation.MainContract.Effect
import shov.studio.ui.anime.navigation.MainContract.Event
import shov.studio.ui.anime.navigation.MainContract.State

internal interface MainContract : UnidirectionalViewModel<State, Event, Effect> {
    @Stable
    data class State(val isAuthorized: Boolean)

    @Stable
    sealed interface Event {
        data class OnTabClick(val tab: AnimeRoute.Main.MainNavBar) : Event
        data class OnItemClick(val type: String, val id: Int) : Event
    }

    sealed interface Effect {
        data class OnItemOpened(val type: String, val id: Int) : Effect
        data class OnTabOpened(val tab: AnimeRoute.Main.MainNavBar) : Effect
    }
}
