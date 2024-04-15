package core.mvi.keepers

import android.content.Intent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@ActivityRetainedScoped
class IntentKeeper @Inject constructor() {
    private val _eventChannel = Channel<Intent>(Channel.BUFFERED)

    fun process(intent: Intent) {
        _eventChannel.trySend(intent)
    }

    fun observe() = _eventChannel.receiveAsFlow()
}
