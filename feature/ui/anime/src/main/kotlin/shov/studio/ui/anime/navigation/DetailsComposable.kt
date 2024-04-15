package shov.studio.ui.anime.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import kotlinx.coroutines.launch
import shov.studio.enums.DetailsStatus
import shov.studio.enums.Status
import core.mvi.collectInLaunchedEffect
import core.mvi.collectStateDispatchEffect
import core.navigator.CustomNavHostController
import core.navigator.compose.effect.BackStackEntryEffect
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.ui.anime.R
import shov.studio.ui.anime.ui.addtolist.AddToListContract
import shov.studio.ui.anime.ui.addtolist.AddToListScreen
import shov.studio.ui.anime.ui.addtolist.AddToListViewModel
import shov.studio.ui.anime.ui.details.DetailsContract.Effect
import shov.studio.ui.anime.ui.details.DetailsScreen
import shov.studio.ui.anime.ui.details.DetailsViewModel
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagContract
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagScreen
import shov.studio.ui.anime.ui.details.addtag.DetailsAddTagViewModel
import shov.studio.ui.anime.ui.details.addtolist.DetailsAddToListContract
import shov.studio.ui.anime.ui.details.addtolist.DetailsAddToListScreen
import shov.studio.ui.anime.ui.details.addtolist.DetailsAddToListViewModel
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagContract
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagScreen
import shov.studio.ui.anime.ui.details.deletetag.DetailsDeleteTagViewModel
import shov.studio.ui.anime.ui.details.setscore.DetailsSetChaptersReadContract
import shov.studio.ui.anime.ui.details.setscore.DetailsSetChaptersReadViewModel
import shov.studio.ui.anime.ui.details.setscore.DetailsSetEpisodesWatchedViewModel
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreContract
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreScreen
import shov.studio.ui.anime.ui.details.setscore.DetailsSetScoreViewModel
import shov.studio.ui.anime.ui.details.setscore.DetailsSetVolumesReadScreen
import shov.studio.ui.anime.ui.details.setscore.DetailsSetVolumesReadViewModel
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesScreen
import shov.studio.ui.anime.ui.detailsnames.DetailsNamesViewModel
import shov.studio.ui.anime.ui.images.ImagesScreen
import shov.studio.ui.anime.ui.images.ImagesViewModel
import shov.studio.ui.anime.ui.images.rememberImagesState
import shov.studio.ui.anime.ui.related.RelatedContract
import shov.studio.ui.anime.ui.related.RelatedScreen
import shov.studio.ui.anime.ui.related.RelatedViewModel
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsContract
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsScreen
import shov.studio.ui.anime.ui.userlist.details.UserListDetailsViewModel
import shov.studio.ui.anime.ui.userlist.sort.UserListSortContract
import shov.studio.ui.anime.ui.userlist.sort.UserListSortScreen
import shov.studio.ui.anime.ui.userlist.sort.UserListSortViewModel
import core.ui.common.scaffold.utils.SnackbarError
import core.ui.navigation.customBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.detailsComposable(navHostController: CustomNavHostController) {
    composable(
        route = AnimeRoute.Related.route,
        arguments = listOf(
            navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
            navArgument(AnimeRoute.Args.type) { type = NavType.StringType }
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        val viewModel: RelatedViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()
        val pullToRefreshState = rememberPullToRefreshState()
        val coroutine = rememberCoroutineScope()

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                event(RelatedContract.Event.OnRefreshClick)
            }
        }

        RelatedScreen(state = state, pullToRefreshState = pullToRefreshState, event = event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                RelatedContract.Effect.OnBack -> navHostController.popBackMainStack()
                is RelatedContract.Effect.OnItemOpen -> navHostController.navHostController
                    .navigate(
                        AnimeRoute.Details.withTypeAndId(collectedEffect.type, collectedEffect.id)
                    )

                RelatedContract.Effect.OnComplete -> pullToRefreshState.endRefresh()
                is RelatedContract.Effect.OnError ->
                    coroutine.launch { state.snackbarHostState.showSnackbar(collectedEffect.error) }

                is RelatedContract.Effect.OnAddToList -> navHostController.navHostController
                    .navigate(
                        AnimeRoute.AddToList.withTypeAndId(collectedEffect.type, collectedEffect.id)
                    )
            }
        }
    }
    customBottomSheet(
        route = AnimeRoute.DetailsNames.route,
        arguments = listOf(
            navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
            navArgument(AnimeRoute.Args.type) { type = NavType.StringType }
        )
    ) {
        val viewModel: DetailsNamesViewModel = hiltViewModel()
        val (state, event) = viewModel.collectStateDispatchEffect()

        DetailsNamesScreen(state = state, event = event)
    }
    composable(
        route = AnimeRoute.Images.route,
        arguments = listOf(
            navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
            navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
            navArgument(AnimeRoute.Args.index) { type = NavType.IntType }
        )
    ) {
        val viewModel: ImagesViewModel = hiltViewModel()

        ImagesScreen(
            state = rememberImagesState(
                viewModel.uiState.collectAsState().value,
                onBackClick = navHostController::popBackMainStack
            )
        )
    }
    navigation(
        startDestination = AnimeRoute.Details.Info.route, route = AnimeRoute.Details.route,
        arguments = listOf(
            navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
            navArgument(AnimeRoute.Args.type) { type = NavType.StringType }
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) },
    ) {
        composable(route = AnimeRoute.Details.Info.route) { backStackEntry ->
            val viewModel: DetailsViewModel =
                hiltViewModel(remember(backStackEntry) { backStackEntry })
            val pullToRefreshState = rememberPullToRefreshState()

            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsScreen(
                detailsState = state,
                pullToRefreshState = pullToRefreshState,
                event = event,
            )

            val resources = LocalContext.current.resources

            effect.collectInLaunchedEffect { ef ->
                when (ef) {
                    Effect.OnBack -> navHostController.popBackMainStack()
                    is Effect.OnIconOpen -> navHostController.navigateMain(
                        AnimeRoute.Images.withTypeIdAndIndex(ef.type, ef.id, ef.index)
                    )

                    is Effect.OnNameOpen -> navHostController.navigateMain(
                        AnimeRoute.DetailsNames.withTypeAndId(ef.type, ef.id)
                    )

                    is Effect.OnAddToList -> navHostController.navigateMain(
                        AnimeRoute.Details.AddToList.withTypeIdAndStatus(ef.type, ef.id, ef.status)
                    )

                    is Effect.OnTagDelete -> navHostController.navigateMain(
                        AnimeRoute.Details.DeleteTag.withTypeIdAndTag(
                            ef.type, ef.id, ef.tag, ef.tags
                        )
                    )

                    is Effect.OnTagAdd -> navHostController.navigateMain(
                        AnimeRoute.Details.AddTag.withTypeIdAndTags(ef.type, ef.id, ef.tags)
                    )

                    is Effect.OnRelatedOpen -> navHostController.navigateMain(
                        AnimeRoute.Related.withTypeAndId(viewModel.type, viewModel.id)
                    )

                    is Effect.OnAnimeOpen -> navHostController.navigateMain(
                        AnimeRoute.Details.withTypeAndId(AnimeRoute.Type.anime, ef.id)
                    )

                    is Effect.OnMangaOpen -> navHostController.navigateMain(
                        AnimeRoute.Details.withTypeAndId(AnimeRoute.Type.manga, ef.id)
                    )

                    is Effect.OnSetScoreOpen -> navHostController.navigateMain(
                        AnimeRoute.Details.SetScore.withTypeIdAndScore(ef.type, ef.id, ef.score)
                    )

                    is Effect.OnErrorShow -> {
                        val result = state.snackbarHostState.showSnackbar(
                            SnackbarError(ef.error, resources.getString(R.string.retry))
                        )

                        viewModel.closeError()

                        if (result == SnackbarResult.ActionPerformed) ef.onError()
                    }

                    is Effect.OnChaptersRead -> navHostController.navigateMain(
                        AnimeRoute.Details.SetChaptersRead.withTypeIdAndChapters(
                            ef.type, ef.id, ef.status, ef.max
                        )
                    )

                    is Effect.OnVolumesRead -> navHostController.navigateMain(
                        AnimeRoute.Details.SetVolumesRead
                            .withTypeIdAndVolumes(ef.type, ef.id, ef.status, ef.max)
                    )

                    is Effect.OnEpisodesWatched -> navHostController.navigateMain(
                        AnimeRoute.Details.SetEpisodesWatched
                            .withTypeIdAndVolumes(ef.type, ef.id, ef.status, ef.max)
                    )

                    Effect.OnRefreshStop -> pullToRefreshState.endRefresh()
                }
            }

            BackStackEntryEffect(
                navHostController = navHostController.navHostController,
                onSuccess = viewModel::onSavedStateChanged
            )
        }

        dialog(
            route = AnimeRoute.Details.AddTag.route,
            arguments = listOf(
                navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
                navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.tags) { type = NavType.StringArrayType;nullable = true }
            )
        ) {
            val viewModel = hiltViewModel<DetailsAddTagViewModel>()

            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsAddTagScreen(state = state, event = event)

            effect.collectInLaunchedEffect { collectedEffect ->
                when (collectedEffect) {
                    is DetailsAddTagContract.Effect.OnClose -> navHostController.popBackMainStack()
                    is DetailsAddTagContract.Effect.OnAdded -> {
                        navHostController.navHostController.previousBackStackEntry?.savedStateHandle
                            ?.set(AnimeRoute.Args.addedTag, collectedEffect.tag)
                        navHostController.popBackMainStack()
                    }
                }
            }
        }

        dialog(
            route = AnimeRoute.Details.DeleteTag.route,
            arguments = listOf(
                navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
                navArgument(AnimeRoute.Args.tag) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.tags) {
                    type = NavType.StringArrayType
                    nullable = false
                }
            )
        ) {
            val viewModel = hiltViewModel<DetailsDeleteTagViewModel>()

            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsDeleteTagScreen(state = state, event = event) {
                event(DetailsDeleteTagContract.Event.OnDeleteClick)
            }

            effect.collectInLaunchedEffect { launchedEffect ->
                when (launchedEffect) {
                    is DetailsDeleteTagContract.Effect.OnDelete -> {
                        navHostController.navHostController.previousBackStackEntry?.savedStateHandle
                            ?.set(AnimeRoute.Args.deletedTag, launchedEffect.tag)

                        navHostController.popBackMainStack()
                    }

                    DetailsDeleteTagContract.Effect.OnCancel -> navHostController.popBackMainStack()
                }
            }
        }

        dialog(
            route = AnimeRoute.Details.SetScore.route,
            arguments = listOf(
                navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
                navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.score) { type = NavType.IntType },
            )
        ) {
            val viewModel = hiltViewModel<DetailsSetScoreViewModel>()

            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsSetScoreScreen(event = event, state = state)

            effect.collectInLaunchedEffect { collectedEffect ->
                when (collectedEffect) {
                    DetailsSetScoreContract.Effect.OnCancel -> navHostController.popBackMainStack()
                    is DetailsSetScoreContract.Effect.OnClose -> {
                        navHostController.navHostController.previousBackStackEntry?.savedStateHandle
                            ?.set(AnimeRoute.Args.score, collectedEffect.score)
                        navHostController.popBackMainStack()
                    }
                }

            }
        }

        dialog(
            route = AnimeRoute.Details.AddToList.route, arguments = listOf(
                navArgument(AnimeRoute.Args.status) { type = NavType.EnumType(Status::class.java) },
                navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
            )
        ) {
            val viewModel = hiltViewModel<DetailsAddToListViewModel>()
            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsAddToListScreen(event = event, state = state)

            effect.collectInLaunchedEffect { collectedEffect ->
                when (collectedEffect) {
                    DetailsAddToListContract.Effect.OnClose ->
                        navHostController.popBackMainStack()

                    is DetailsAddToListContract.Effect.OnStatusSet -> {
                        navHostController.navHostController.previousBackStackEntry
                            ?.savedStateHandle?.set(AnimeRoute.Args.status, collectedEffect.status)

                        navHostController.popBackMainStack()

                    }
                }
            }
        }

        dialog(
            route = AnimeRoute.Details.SetChaptersRead.route, arguments = listOf(
                navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
                navArgument(AnimeRoute.Args.status) {
                    type = NavType.EnumType(DetailsStatus::class.java)
                },
                navArgument(AnimeRoute.Args.max) { type = NavType.IntType }
            )
        ) {
            val viewModel = hiltViewModel<DetailsSetChaptersReadViewModel>()
            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsSetVolumesReadScreen(state, event)

            effect.collectInLaunchedEffect { ef ->
                when (ef) {
                    DetailsSetChaptersReadContract.Effect.OnCancel ->
                        navHostController.popBackMainStack()

                    is DetailsSetChaptersReadContract.Effect.OnClose -> {
                        navHostController.navHostController.previousBackStackEntry
                            ?.savedStateHandle?.set(AnimeRoute.Args.chapters, ef.value)

                        navHostController.popBackMainStack()
                    }
                }
            }
        }

        dialog(
            route = AnimeRoute.Details.SetVolumesRead.route, arguments = listOf(
                navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
                navArgument(AnimeRoute.Args.status) {
                    type = NavType.EnumType(DetailsStatus::class.java)
                },
                navArgument(AnimeRoute.Args.max) { type = NavType.IntType }
            )
        ) {
            val viewModel = hiltViewModel<DetailsSetVolumesReadViewModel>()
            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsSetVolumesReadScreen(state, event)

            effect.collectInLaunchedEffect { ef ->
                when (ef) {
                    DetailsSetChaptersReadContract.Effect.OnCancel ->
                        navHostController.popBackMainStack()

                    is DetailsSetChaptersReadContract.Effect.OnClose -> {
                        navHostController.navHostController.previousBackStackEntry
                            ?.savedStateHandle?.set(AnimeRoute.Args.volumes, ef.value)

                        navHostController.popBackMainStack()
                    }
                }
            }
        }

        dialog(
            route = AnimeRoute.Details.SetEpisodesWatched.route, arguments = listOf(
                navArgument(AnimeRoute.Args.type) { type = NavType.StringType },
                navArgument(AnimeRoute.Args.id) { type = NavType.IntType },
                navArgument(AnimeRoute.Args.status) {
                    type = NavType.EnumType(DetailsStatus::class.java)
                },
                navArgument(AnimeRoute.Args.max) { type = NavType.IntType }
            )
        ) {
            val viewModel = hiltViewModel<DetailsSetEpisodesWatchedViewModel>()
            val (state, event, effect) = viewModel.collectStateDispatchEffect()

            DetailsSetVolumesReadScreen(state, event)

            effect.collectInLaunchedEffect { ef ->
                when (ef) {
                    DetailsSetChaptersReadContract.Effect.OnCancel ->
                        navHostController.popBackMainStack()

                    is DetailsSetChaptersReadContract.Effect.OnClose -> {
                        navHostController.navHostController.previousBackStackEntry
                            ?.savedStateHandle?.set(AnimeRoute.Args.episodes, ef.value)

                        navHostController.popBackMainStack()
                    }
                }
            }
        }
    }
    composable(
        route = AnimeRoute.UserList.route,
        arguments = listOf(
            navArgument(AnimeRoute.Args.type) { type = NavType.StringType }
        ),
        enterTransition = { slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left) },
        popEnterTransition = { fadeIn() },
        popExitTransition = { slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right) }
    ) {
        val viewModel: UserListDetailsViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()

        UserListDetailsScreen(state = state, event = event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                UserListDetailsContract.Effect.OnBack -> navHostController.popBackMainStack()
                is UserListDetailsContract.Effect.OnItemOpen -> navHostController.navHostController
                    .navigate(
                        AnimeRoute.Details.withTypeAndId(collectedEffect.type, collectedEffect.id)
                    )

                is UserListDetailsContract.Effect.OnSortOpen -> navHostController.navHostController
                    .navigate(
                        AnimeRoute.UserListSort.withTypeAndSort(
                            collectedEffect.type, collectedEffect.sort
                        )
                    )

                is UserListDetailsContract.Effect.OnAddToListOpen -> navHostController
                    .navHostController.navigate(
                        AnimeRoute.AddToList.withTypeAndId(collectedEffect.type, collectedEffect.id)
                    )
            }
        }

        BackStackEntryEffect(
            navHostController = navHostController.navHostController,
            onSuccess = viewModel::onSavedStateChanged
        )
    }

    dialog(AnimeRoute.UserListSort.route) {
        val viewModel: UserListSortViewModel = hiltViewModel()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()

        UserListSortScreen(state, event)

        effect.collectInLaunchedEffect { collectedEffect ->
            when (collectedEffect) {
                UserListSortContract.Effect.OnCancel ->
                    navHostController.popBackMainStack()

                is UserListSortContract.Effect.OnSave -> {
                    navHostController.navHostController.previousBackStackEntry?.savedStateHandle
                        ?.set(AnimeRoute.Args.sort, collectedEffect.sort)
                    navHostController.popBackMainStack()
                }
            }
        }
    }

    composable(AnimeRoute.AddToList.route) {
        val viewModel = hiltViewModel<AddToListViewModel>()
        val (state, event, effect) = viewModel.collectStateDispatchEffect()

        AddToListScreen(state, event)

        effect.collectInLaunchedEffect { ef ->
            when (ef) {
                AddToListContract.Effect.OnCancel -> navHostController.popBackMainStack()
                AddToListContract.Effect.OnComplete -> navHostController.popBackMainStack()
            }
        }
    }
}
