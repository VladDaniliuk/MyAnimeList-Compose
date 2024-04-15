package core.navigator.utils

import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController

fun NavHostController.isResumed() =
    currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
