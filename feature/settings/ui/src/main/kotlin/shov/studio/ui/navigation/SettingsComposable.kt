package shov.studio.ui.navigation

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import core.mvi.collectInLaunchedEffect
import core.mvi.collectStateDispatchEffect
import core.navigator.CustomNavHostController
import shov.studio.settings.navigator.SettingsRoute
import shov.studio.settings.ui.BuildConfig
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.ui.settings.SettingsContract.Effect
import shov.studio.ui.ui.settings.SettingsContract.Event
import shov.studio.ui.ui.settings.SettingsContract.State
import shov.studio.ui.ui.settings.SettingsScreen
import shov.studio.ui.ui.settings.SettingsViewModel

fun NavGraphBuilder.settingsComposable(navHostController: CustomNavHostController) {
    dialog(SettingsRoute.Settings.route) {
        val viewModel: SettingsViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect<State, Event, Effect>()
        val context = LocalContext.current
        val uriHandler = LocalUriHandler.current

        SettingsScreen(state = state, event = event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                Effect.OnBack -> navHostController.popBackMainStack()
                is Effect.OnLogIn -> {
                    CustomTabsIntent.Builder().build().launchUrl(context, collectedEffect.uri)
                }

                Effect.OnUser -> {
                    navHostController.popBackMainStack()
                    navHostController.navigateBottomAppBar(AnimeRoute.Main.MainNavBar.User.route)
                }

                Effect.OnUriOpen -> uriHandler.openUri(BuildConfig.PRIVACY_LINK)
                Effect.OnAccountManagementOpen -> navHostController.navHostController
                    .navigate(AnimeRoute.AccountManagement.route)

                Effect.OnAccountChanged -> {
                    navHostController.bottomAppBarNavHostController.clearBackStack(AnimeRoute.Main.MainNavBar.User.route)
                    navHostController.bottomAppBarNavHostController.clearBackStack(AnimeRoute.Main.MainNavBar.UserList.route)
                    navHostController.bottomAppBarNavHostController.clearBackStack(AnimeRoute.Main.MainNavBar.Suggested.route)
                }
            }
        }
    }
}
