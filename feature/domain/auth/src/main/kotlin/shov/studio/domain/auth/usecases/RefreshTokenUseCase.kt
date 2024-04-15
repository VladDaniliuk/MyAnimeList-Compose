package shov.studio.domain.auth.usecases

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import shov.studio.domain.auth.worker.RefreshTokenWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke() {
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "RefreshTokenWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            PeriodicWorkRequestBuilder<RefreshTokenWorker>(55, TimeUnit.MINUTES)
                .addTag("RefreshTokenTag")
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                ).build()
        )
    }
}
