package shov.studio.ui.anime.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import core.mvi.collectInLaunchedEffect
import core.mvi.collectStateDispatchEffect
import core.navigator.CustomNavHostController
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.navigation.MainContract.Effect
import shov.studio.ui.anime.ui.main.MainNavHost
import shov.studio.ui.anime.ui.main.MainScreen

fun NavGraphBuilder.mainComposable(navHostController: CustomNavHostController) {
    composable(
        route = AnimeRoute.Main.route,
        exitTransition = { fadeOut() },
        popEnterTransition = { fadeIn() }
    ) {
        val viewModel: MainViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()
        val route = navHostController.getCurrentBottomAppBarRouteAsState()

        MainScreen(state = state, route = route, event = event) { paddingValues ->
            MainNavHost(
                modifier = Modifier.padding(paddingValues),
                navHostController = navHostController,
            )
        }

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                is Effect.OnItemOpened -> {
                    navHostController.navHostController.navigate(
                        AnimeRoute.Details.withTypeAndId(collectedEffect.type, collectedEffect.id)
                    )
                }

                is Effect.OnTabOpened -> navHostController
                    .navigateBottomAppBar(collectedEffect.tab.route)
            }
        }
    }
}
