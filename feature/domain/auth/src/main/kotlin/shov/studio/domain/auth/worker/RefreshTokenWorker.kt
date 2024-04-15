package shov.studio.domain.auth.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.repositories.AuthRepository

@HiltWorker
class RefreshTokenWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val authRepository: AuthRepository,
    private val accountsRepository: AccountsRepository,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val accountId = accountsRepository.getLastUsedAccountId() ?: return Result.failure()

        return authRepository.refreshToken(accountId).fold(
            onSuccess = { Result.success() },
            onFailure = { throwable ->
                if (throwable is NullPointerException) Result.failure() else Result.retry()
            }
        )
    }
}
