package shov.studio.ui.anime.ui.filter

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import core.mvi.collectInLaunchedEffect
import core.mvi.collectStateDispatchEffect
import core.navigator.CustomNavHostController
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.ui.filterresult.FilterResultContract
import shov.studio.ui.anime.ui.filterresult.FilterResultScreen
import shov.studio.ui.anime.ui.filterresult.FilterResultViewModel

fun NavGraphBuilder.filterComposable(navHostController: CustomNavHostController) {
    composable(
        route = AnimeRoute.Filter.route,
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        FilterScreen(onBackClick = navHostController::popBackMainStack) { year, season, sortType ->
            navHostController.navigateMain(AnimeRoute.FilterResult.withArgs(year, season, sortType))
        }
    }
    composable(
        route = AnimeRoute.FilterResult.route,
        arguments = listOf(
            navArgument(AnimeRoute.Args.year) { type = NavType.IntType },
            navArgument(AnimeRoute.Args.season) { type = NavType.StringType },
            navArgument(AnimeRoute.Args.sort) { type = NavType.StringType }
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        val viewModel: FilterResultViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()

        FilterResultScreen(state, event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                is FilterResultContract.Effect.OnAddToList -> navHostController.navHostController
                    .navigate(
                        AnimeRoute.AddToList.withTypeAndId(
                            AnimeRoute.Type.anime, collectedEffect.id
                        )
                    )

                is FilterResultContract.Effect.OnAnimeOpen -> navHostController.navHostController
                    .navigate(
                        AnimeRoute.Details.withTypeAndId(AnimeRoute.Type.anime, collectedEffect.id)
                    )

                FilterResultContract.Effect.OnBack -> navHostController.popBackMainStack()
            }
        }
    }
}
