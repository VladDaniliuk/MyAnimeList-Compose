package data.common.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import shov.studio.enums.RankingType
import shov.studio.enums.Sort
import shov.studio.enums.Status
import data.common.datas.StandardResponse
import data.common.sources.StandardPagingSource
import data.common.sources.StandardSource
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource
import javax.inject.Inject

class StandardRepository @Inject constructor(
    private val standardSource: StandardSource,
    private val accountPreferencesSource: AccountPreferencesSource,
    private val accountInfoSource: AccountInfoSource,
) {
    fun search(type: String, name: String) = Pager(PagingConfig(pageSize = 20)) {
        StandardPagingSource { pageOffset -> standardSource.search(type, name, pageOffset) }
    }.flow

    suspend fun detailsNames(type: String, id: Int) = standardSource.detailsNames(type, id)

    fun rankingList(type: String, rankingType: RankingType) = Pager(PagingConfig(pageSize = 10)) {
        StandardPagingSource { pageOffset -> standardSource.get(type, rankingType, pageOffset) }
    }.flow

    suspend fun related(id: Int, type: String) = standardSource.related(id, type)

    suspend fun images(type: String, id: Int) = standardSource.images(type, id)

    suspend fun details(type: String, id: Int) = standardSource.details(
        type, id,
        accountInfoSource.getLastUsedAccountId()?.let(accountPreferencesSource::getAccessToken)
            ?.getOrNull()
    )

    fun userList(type: String, status: Status, sort: Sort) =
        Pager(PagingConfig(10)) {
            StandardPagingSource { offset ->
                val accountId = accountInfoSource.getLastUsedAccountId()
                    ?: return@StandardPagingSource Result.failure(Error("Last account doesn't exist"))

                accountPreferencesSource.getAccessToken(accountId).fold(
                    onSuccess = { token ->
                        standardSource.userList(type, offset, status, sort, token)
                    },
                    onFailure = Result.Companion::failure
                )
            }
        }.flow

    suspend fun userListPreview(
        type: String, status: Status, sort: Sort
    ): Result<StandardResponse> {
        val accountId = accountInfoSource.getLastUsedAccountId()
            ?: return Result.failure(Error("Last account doesn't exist"))

        return accountPreferencesSource.getAccessToken(accountId).fold(
            onSuccess = { token -> standardSource.userList(type, 0, status, sort, token) },
            onFailure = Result.Companion::failure
        )
    }
}
