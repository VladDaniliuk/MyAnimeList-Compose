package shov.studio.trackanimelist

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class TrackAnimeListApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override lateinit var workManagerConfiguration: Configuration

    override fun onCreate() {
        super.onCreate()

        workManagerConfiguration = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.DEBUG).setWorkerFactory(workerFactory).build()
    }
}
