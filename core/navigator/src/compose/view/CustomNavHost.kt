package core.navigator.compose.view

import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import core.navigator.CustomNavHostController
import core.navigator.rememberCustomNavHostController

//TODO remove, when bottomsheet will have material you integration
@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CustomNavHost(
    startDestination: String,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.(CustomNavHostController) -> Unit,
) {
    val customNavHostController = rememberCustomNavHostController()

    ModalBottomSheetLayout(
        modifier = modifier,
        bottomSheetNavigator = customNavHostController.bottomSheetNavigator,
        sheetShape = BottomSheetDefaults.ExpandedShape,
        sheetElevation = BottomSheetDefaults.Elevation,
        sheetBackgroundColor = MaterialTheme.colorScheme.surface,
        sheetContentColor = contentColorFor(MaterialTheme.colorScheme.surface),
        scrimColor = BottomSheetDefaults.ScrimColor
    ) {
        NavHost(
            navController = customNavHostController.navHostController,
            startDestination = startDestination,
        ) {
            builder(customNavHostController)
        }
    }
}
