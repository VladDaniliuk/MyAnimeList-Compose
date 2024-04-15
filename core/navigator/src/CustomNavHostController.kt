package core.navigator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator
import core.navigator.utils.isResumed

class CustomNavHostController @OptIn(ExperimentalMaterialNavigationApi::class) constructor(
    val navHostController: NavHostController,
    val bottomAppBarNavHostController: NavHostController,
    val bottomSheetNavigator: BottomSheetNavigator,
) {
    fun popBackMainStack() {
        if (navHostController.isResumed()) navHostController.popBackStack()
    }

    fun navigateMain(route: String) {
        navHostController.navigate(route)
    }

    fun navigateBottomAppBar(route: String) {
        bottomAppBarNavHostController.navigate(route) {
            popUpTo(bottomAppBarNavHostController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    @Composable
    fun getCurrentBottomAppBarRouteAsState() =
        bottomAppBarNavHostController.currentBackStackEntryAsState().value?.destination?.route
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun rememberCustomNavHostController(): CustomNavHostController {
    val bottomSheetNavigator = rememberBottomSheetNavigator()
    val navHostController = rememberNavController(bottomSheetNavigator)
    val bottomAppBarNavHostController = rememberNavController()

    return remember {
        CustomNavHostController(
            navHostController,
            bottomAppBarNavHostController,
            bottomSheetNavigator
        )
    }
}
