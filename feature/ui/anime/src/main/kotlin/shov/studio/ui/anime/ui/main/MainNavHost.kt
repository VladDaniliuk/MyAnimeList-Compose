package shov.studio.ui.anime.ui.main

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import core.mvi.collectInLaunchedEffect
import core.mvi.collectStateDispatchEffect
import core.navigator.CustomNavHostController
import shov.studio.settings.navigator.SettingsRoute
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.ui.main.StandardListContract.Effect.OnAddToList
import shov.studio.ui.anime.ui.main.StandardListContract.Effect.OnFilterOpen
import shov.studio.ui.anime.ui.main.StandardListContract.Effect.OnItemOpen
import shov.studio.ui.anime.ui.main.StandardListContract.Effect.OnSettingsOpen
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeContract.Effect
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeScreen
import shov.studio.ui.anime.ui.suggested.SuggestedAnimeViewModel
import shov.studio.ui.anime.ui.userlist.main.UserListContract
import shov.studio.ui.anime.ui.userlist.main.UserListScreen
import shov.studio.ui.anime.ui.userlist.main.UserListViewModel
import shov.studio.ui.user.navigation.userComposable

@Composable
internal fun MainNavHost(
    navHostController: CustomNavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController.bottomAppBarNavHostController,
        startDestination = AnimeRoute.Main.MainNavBar.Anime.route
    ) {
        composable(
            route = AnimeRoute.Main.MainNavBar.Anime.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            val viewModel: AnimeViewModel = hiltViewModel()
            val (animeState, event, effect) = viewModel.collectStateDispatchEffect()
            val searchList = viewModel.searchList.collectAsLazyPagingItems()

            StandardListScreen(searchDetails = searchList, state = animeState, event = event)

            effect.collectInLaunchedEffect { collectedEffect ->
                when (collectedEffect) {
                    OnFilterOpen -> navHostController.navHostController
                        .navigate(AnimeRoute.Filter.route)

                    is OnItemOpen -> navHostController.navHostController
                        .navigate(
                            AnimeRoute.Details
                                .withTypeAndId(AnimeRoute.Type.anime, collectedEffect.id)
                        )

                    OnSettingsOpen -> navHostController.navHostController
                        .navigate(SettingsRoute.Settings.route)

                    is OnAddToList -> navHostController.navHostController.navigate(
                        AnimeRoute.AddToList.withTypeAndId(collectedEffect.type, collectedEffect.id)
                    )
                }
            }
        }

        composable(
            route = AnimeRoute.Main.MainNavBar.Manga.route,
            enterTransition = {
                when (initialState.destination.route) {
                    AnimeRoute.Main.MainNavBar.Anime.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

                    else -> slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    AnimeRoute.Main.MainNavBar.Anime.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)

                    else -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    AnimeRoute.Main.MainNavBar.Anime.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)

                    else -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }
            }
        ) {
            val viewModel: MangaViewModel = hiltViewModel()
            val searchList = viewModel.searchList.collectAsLazyPagingItems()
            val (animeState, event, effect) = viewModel.collectStateDispatchEffect()

            StandardListScreen(searchDetails = searchList, state = animeState, event = event)

            effect.collectInLaunchedEffect { collectedEffect ->
                when (collectedEffect) {
                    OnFilterOpen -> navHostController
                        .navHostController.navigate(AnimeRoute.Filter.route)

                    is OnItemOpen -> navHostController.navHostController.navigate(
                        AnimeRoute.Details.withTypeAndId(AnimeRoute.Type.manga, collectedEffect.id)
                    )

                    OnSettingsOpen -> navHostController
                        .navHostController.navigate(SettingsRoute.Settings.route)

                    is OnAddToList -> navHostController.navHostController.navigate(
                        AnimeRoute.AddToList.withTypeAndId(collectedEffect.type, collectedEffect.id)
                    )
                }

            }
        }

        composable(
            route = AnimeRoute.Main.MainNavBar.Suggested.route,
            enterTransition = {
                when (initialState.destination.route) {
                    AnimeRoute.Main.MainNavBar.Anime.route, AnimeRoute.Main.MainNavBar.Manga.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)

                    else -> slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    AnimeRoute.Main.MainNavBar.Anime.route, AnimeRoute.Main.MainNavBar.Manga.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)

                    else -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    AnimeRoute.Main.MainNavBar.Anime.route, AnimeRoute.Main.MainNavBar.Manga.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)

                    else -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }
            }
        ) {
            val viewModel: SuggestedAnimeViewModel = hiltViewModel()
            val (suggestedState, event, effect) = viewModel.collectStateDispatchEffect()

            SuggestedAnimeScreen(suggestedState, event)

            effect.collectInLaunchedEffect { collectedEffect ->
                when (collectedEffect) {
                    is Effect.OnAnimeOpened -> navHostController.navHostController.navigate(
                        AnimeRoute.Details.withTypeAndId(AnimeRoute.Type.anime, collectedEffect.id)
                    )

                    is Effect.OnAddToListOpened -> navHostController.navHostController.navigate(
                        AnimeRoute.AddToList.withTypeAndId(
                            AnimeRoute.Type.anime, collectedEffect.id
                        )
                    )
                }
            }
        }

        composable(
            route = AnimeRoute.Main.MainNavBar.UserList.route,
            enterTransition = {
                when (initialState.destination.route) {
                    AnimeRoute.Main.MainNavBar.User.route ->
                        slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)

                    else -> slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                }
            }, exitTransition = {
                when (targetState.destination.route) {
                    AnimeRoute.Main.MainNavBar.User.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)

                    else -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    AnimeRoute.Main.MainNavBar.User.route ->
                        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)

                    else -> slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }
            }
        ) {
            val viewModel: UserListViewModel = hiltViewModel()
            val (userListState, event, effect) = viewModel.collectStateDispatchEffect()

            UserListScreen(userListState, event)

            effect.collectInLaunchedEffect { collectedEffect ->
                when (collectedEffect) {
                    is UserListContract.Effect.OnItemOpen -> navHostController.navHostController
                        .navigate(
                            AnimeRoute.Details
                                .withTypeAndId(collectedEffect.type, collectedEffect.id)
                        )

                    is UserListContract.Effect.OnListOpen -> navHostController.navHostController
                        .navigate(AnimeRoute.UserList.withType(collectedEffect.type))
                }
            }
        }

        userComposable(navHostController)
    }
}
