package shov.studio.ui.anime.ui.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import shov.studio.trackanimelist.navigator.AnimeRoute.Main.MainNavBar
import shov.studio.ui.anime.navigation.MainContract.Event
import shov.studio.ui.anime.navigation.MainContract.State

@Composable
internal fun MainScreen(
    state: State,
    route: String?,
    event: (Event) -> Unit,
    content: @Composable (paddingValues: PaddingValues) -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.navigationBars,
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = route == MainNavBar.Anime.route,
                    onClick = { event(Event.OnTabClick(MainNavBar.Anime)) },
                    icon = {
                        Icon(
                            imageVector = MainNavBar.Anime.icon,
                            contentDescription = stringResource(MainNavBar.Anime.titleId)
                        )
                    },
                    label = { Text(text = stringResource(MainNavBar.Anime.titleId)) }
                )

                NavigationBarItem(
                    selected = route == MainNavBar.Manga.route,
                    onClick = { event(Event.OnTabClick(MainNavBar.Manga)) },
                    icon = {
                        Icon(
                            imageVector = MainNavBar.Manga.icon,
                            contentDescription = stringResource(MainNavBar.Manga.titleId)
                        )
                    },
                    label = { Text(text = stringResource(MainNavBar.Manga.titleId)) }
                )

                if (state.isAuthorized) NavigationBarItem(
                    selected = route == MainNavBar.Suggested.route,
                    onClick = { event(Event.OnTabClick(MainNavBar.Suggested)) },
                    icon = {
                        Icon(
                            imageVector = MainNavBar.Suggested.icon,
                            contentDescription = stringResource(MainNavBar.Suggested.titleId)
                        )
                    },
                    label = { Text(text = stringResource(MainNavBar.Suggested.titleId)) }
                )

                if (state.isAuthorized) NavigationBarItem(
                    selected = route == MainNavBar.UserList.route,
                    onClick = { event(Event.OnTabClick(MainNavBar.UserList)) },
                    icon = {
                        Icon(
                            imageVector = MainNavBar.UserList.icon,
                            contentDescription = stringResource(MainNavBar.UserList.titleId)
                        )
                    },
                    label = { Text(text = stringResource(MainNavBar.UserList.titleId)) }
                )

                NavigationBarItem(
                    selected = route == MainNavBar.User.route,
                    onClick = { event(Event.OnTabClick(MainNavBar.User)) },
                    icon = {
                        Icon(
                            imageVector = MainNavBar.User.icon,
                            contentDescription = stringResource(MainNavBar.User.titleId)
                        )
                    },
                    label = { Text(text = stringResource(MainNavBar.User.titleId)) }
                )
            }
        }
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Preview
@Composable
private fun MainPreview() {
    MainScreen(state = State(true), route = MainNavBar.Manga.route, event = {}) {}
}
