package data.user.repositories

import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.sources.AccountPreferencesSource
import data.user.models.UserFullResponse
import data.user.models.UserResponse
import data.user.sources.UserSource
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userSource: UserSource,
    private val accountPreferencesSource: AccountPreferencesSource,
    private val accountsRepository: AccountsRepository,
) {
    suspend fun get(): Result<UserResponse> {
        val accountId = accountsRepository.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))


        return accountPreferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token -> userSource.get(token) },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun getFull(): Result<UserFullResponse> {
        val accountId = accountsRepository.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return accountPreferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token -> userSource.getFull(token) },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun getFull(token: String) = userSource.getFull(token)
    suspend fun get(token: String) = userSource.get(token)
}
