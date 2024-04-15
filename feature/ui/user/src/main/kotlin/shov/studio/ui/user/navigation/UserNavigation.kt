package shov.studio.ui.user.navigation

import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import core.mvi.collectInLaunchedEffect
import core.mvi.collectStateDispatchEffect
import core.navigator.CustomNavHostController
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.user.ui.user.UserContract
import shov.studio.ui.user.ui.user.UserFullScreen
import shov.studio.ui.user.ui.user.UserFullViewModel

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.userComposable(navHostController: CustomNavHostController) {
    composable(
        route = AnimeRoute.Main.MainNavBar.User.route,
        enterTransition = {
            slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
        }, exitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        },
        popExitTransition = {
            slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
        }
    ) {
        val viewModel: UserFullViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()
        val context = LocalContext.current
        val pullToRefreshState = rememberPullToRefreshState()

        UserFullScreen(state, pullToRefreshState, event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                is UserContract.Effect.OnLinkLoaded -> CustomTabsIntent.Builder().build()
                    .launchUrl(context, collectedEffect.link)

                UserContract.Effect.OnRefreshFinish -> pullToRefreshState.endRefresh()
                is UserContract.Effect.OnLogOut -> navHostController.navHostController
                    .navigate(AnimeRoute.LogOut.withId(collectedEffect.id))
            }
        }
    }
}
