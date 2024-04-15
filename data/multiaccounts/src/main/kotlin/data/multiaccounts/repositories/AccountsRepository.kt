package data.multiaccounts.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import data.multiaccounts.models.AccountResponse
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource
import javax.inject.Inject

class AccountsRepository @Inject constructor(
    private val accountInfoSource: AccountInfoSource,
    private val accountPreferencesSource: AccountPreferencesSource,
) {
    suspend fun updateAccount(account: AccountResponse) {
        accountInfoSource.upsertAccount(account)
    }

    suspend fun getAccount(id: String) = accountInfoSource.getAccount(id)

    suspend fun deleteAccount(id: String) {
        accountInfoSource.deleteAccount(id)

        accountPreferencesSource.deleteAccount(id)
    }

    fun getAccounts() = Pager(config = PagingConfig(pageSize = 10)) {
        accountInfoSource.getAccounts()
    }.flow

    fun getLastUsedAccounts() = accountInfoSource.getLastUsedAccounts()

    suspend fun getLastUsedAccountId() = accountInfoSource.getLastUsedAccountId()

    fun getLastUsedAccount() = accountInfoSource.getLastUsedAccount()
}
