package core.navigator.compose.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BackStackEntryEffect(
    navHostController: NavHostController,
    onSuccess: SavedStateHandle.() -> Unit,
) {
    val backStackEntry by navHostController.currentBackStackEntryAsState()

    LaunchedEffect(key1 = backStackEntry) {
        backStackEntry?.savedStateHandle?.onSuccess()
    }
}
