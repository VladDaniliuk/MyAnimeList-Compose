package shov.studio.trackanimelist

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import core.navigator.compose.view.CustomNavHost
import shov.studio.trackanimelist.navigator.AnimeRoute
import shov.studio.theme.ui.AllApisTheme
import shov.studio.ui.anime.navigation.detailsComposable
import shov.studio.ui.anime.navigation.mainComposable
import shov.studio.ui.anime.ui.filter.filterComposable
import shov.studio.ui.navigation.settingsComposable
import shov.studio.ui.user.navigation.userManagementComposable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        enableEdgeToEdge()

        viewModel.refreshToken()

        setContent {
            AllApisTheme {
                CustomNavHost(startDestination = AnimeRoute.Main.route) { navHostController ->
                    mainComposable(navHostController = navHostController)
                    filterComposable(navHostController = navHostController)
                    detailsComposable(navHostController = navHostController)
                    settingsComposable(navHostController = navHostController)
                    userManagementComposable(navHostController = navHostController)
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        viewModel.onNewIntent(intent)
    }
}
