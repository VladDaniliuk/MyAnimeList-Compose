package shov.studio.domain.auth.usecases

import android.content.Context
import android.net.Uri
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.qualifiers.ApplicationContext
import data.multiaccounts.models.AccountResponse
import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.repositories.AuthRepository
import data.user.models.UserFullResponse
import data.user.repositories.UserRepository
import shov.studio.domain.auth.worker.RefreshTokenWorker
import shov.studio.feature.domain.models.data.converters.toEntity
import shov.studio.feature.domain.models.data.models.UserFullEntity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthFullUserUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val accountsRepository: AccountsRepository,
    @ApplicationContext private val context: Context,
) {
    suspend operator fun invoke(uri: Uri): Result<UserFullEntity> {
        val code = uri.getQueryParameter("code")
            ?: return Result.failure(NullPointerException("No code in uri"))

        return authRepository.changeTokens(code).fold(onSuccess = { token ->

            userRepository.getFull(token.accessToken).onSuccess { response ->
                authRepository.setTokens(response.id, token)

                accountsRepository.updateAccount(
                    AccountResponse(response.id, response.name, response.picture)
                )

                WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                    "RefreshTokenWorker",
                    ExistingPeriodicWorkPolicy.KEEP,
                    PeriodicWorkRequestBuilder<RefreshTokenWorker>(55, TimeUnit.MINUTES)
                        .addTag("RefreshTokenTag")
                        .setInitialDelay(55, TimeUnit.MINUTES)
                        .setConstraints(
                            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED)
                                .build()
                        ).build()
                )
            }.map(UserFullResponse::toEntity)
        }, onFailure = Result.Companion::failure)
    }
}
