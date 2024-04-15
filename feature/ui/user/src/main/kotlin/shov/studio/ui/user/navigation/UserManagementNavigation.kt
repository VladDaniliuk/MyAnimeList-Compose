package shov.studio.ui.user.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import core.mvi.collectInLaunchedEffect
import core.mvi.collectStateDispatchEffect
import core.navigator.CustomNavHostController
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementContract
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementScreen
import shov.studio.ui.user.ui.accauntmanagement.AccountManagementViewModel
import shov.studio.ui.user.ui.logout.LogOutContract
import shov.studio.ui.user.ui.logout.LogOutScreen
import shov.studio.ui.user.ui.logout.LogOutViewModel

fun NavGraphBuilder.userManagementComposable(navHostController: CustomNavHostController) {
    composable(AnimeRoute.AccountManagement.route) {
        val viewModel: AccountManagementViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()

        AccountManagementScreen(state, event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                AccountManagementContract.Effect.OnBack -> navHostController.popBackMainStack()
            }
        }
    }

    dialog(route = AnimeRoute.LogOut.route) {
        val viewModel: LogOutViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()

        LogOutScreen(state, event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                LogOutContract.Effect.OnCancel -> {
                    navHostController.popBackMainStack()

                    navHostController.bottomAppBarNavHostController.navigate(
                        AnimeRoute.Main.MainNavBar.Anime.route
                    ) {
                        popUpTo(AnimeRoute.Main.MainNavBar.Anime.route) {
                            inclusive = true
                            saveState = false
                        }
                    }
                }
            }
        }
    }
}
