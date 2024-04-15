package core.ui.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.bottomSheet

//TODO remove, when bottomsheet will have material you integration
@OptIn(ExperimentalMaterialNavigationApi::class)
fun NavGraphBuilder.customBottomSheet(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (backstackEntry: NavBackStackEntry) -> Unit
) {
    bottomSheet(route = route, arguments = arguments, deepLinks = deepLinks) { navBackStackEntry ->
        Surface(contentColor = contentColorFor(MaterialTheme.colorScheme.surface)) {
            content(navBackStackEntry)
        }
    }
}
