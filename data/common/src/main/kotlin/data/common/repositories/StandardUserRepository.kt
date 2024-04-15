package data.common.repositories

import shov.studio.enums.Status
import data.common.datas.StandardListStatusResponse
import data.common.sources.StandardUserSource
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource
import javax.inject.Inject

class StandardUserRepository @Inject constructor(
    private val standardUserSource: StandardUserSource,
    private val preferencesSource: AccountPreferencesSource,
    private val accountInfoSource: AccountInfoSource,
) {
    suspend fun updateStatus(
        type: String, id: Int, status: Status
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token -> standardUserSource.updateStatus(type, id, status, token) },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateScore(type: String, id: Int, score: Int): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token -> standardUserSource.updateScore(type, id, score, token) },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateTags(
        type: String, id: Int, tags: List<String>
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token -> standardUserSource.updateTags(type, id, tags, token) },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateComments(
        type: String, id: Int, comments: String
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token -> standardUserSource.updateComments(type, id, comments, token) },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateIsRewatching(
        type: String, id: Int, isRewatching: Boolean
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token ->
                standardUserSource.updateIsRewatching(type, id, isRewatching, token)
            },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateNumChaptersRead(
        id: Int, numChaptersRead: Int
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token ->
                standardUserSource.updateNumChaptersRead(id, numChaptersRead, token)
            },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateNumVolumesRead(
        id: Int, numVolumesRead: Int
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token ->
                standardUserSource.updateNumVolumesRead(id, numVolumesRead, token)
            },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateNumEpisodesWatched(
        id: Int, numVolumesRead: Int
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token ->
                standardUserSource.updateNumEpisodesWatched(id, numVolumesRead, token)
            },
            onFailure = Result.Companion::failure
        )
    }

    suspend fun updateDetails(
        type: String, id: Int, status: Status, score: Int, tags: List<String>, comment: String,
        isRewatching: Boolean, numChaptersRead: Int?, numVolumesRead: Int?,
        numWatchedEpisodes: Int?,
    ): Result<StandardListStatusResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return preferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token ->
                standardUserSource.updateDetails(
                    type, id, status, score, tags, comment, isRewatching, numChaptersRead,
                    numVolumesRead, numWatchedEpisodes, token
                )
            }, onFailure = Result.Companion::failure
        )
    }
}