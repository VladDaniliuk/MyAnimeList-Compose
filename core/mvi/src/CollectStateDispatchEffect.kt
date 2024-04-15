package core.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
inline fun <reified STATE, EVENT, EFFECT> UnidirectionalViewModel<STATE, EVENT, EFFECT>.collectStateDispatchEffect(): StateDispatchEffect<STATE, EVENT, EFFECT> {
    val state by state.collectAsStateWithLifecycle()

    val dispatch: (EVENT) -> Unit = { event ->
        event(event)
    }

    return StateDispatchEffect(
        state = state,
        effectFlow = effect,
        dispatch = dispatch,
    )
}
