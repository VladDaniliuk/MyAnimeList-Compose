package data.multiaccounts.sources

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import data.multiaccounts.models.AccountResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountInfoSource {
    @Upsert(entity = AccountResponse::class)
    suspend fun upsertAccount(accountResponse: AccountResponse)

    @Query("SELECT * FROM accounts WHERE id = :id")
    suspend fun getAccount(id: String): AccountResponse

    @Query("DELETE FROM accounts WHERE id = :id")
    suspend fun deleteAccount(id: String)

    @Query("SELECT * FROM accounts ORDER BY last_use DESC")
    fun getAccounts(): PagingSource<Int, AccountResponse>

    @Query("SELECT * FROM accounts ORDER BY last_use DESC LIMIT 3 OFFSET 1")
    fun getLastUsedAccounts(): Flow<List<AccountResponse>>

    @Query("SELECT id FROM accounts ORDER BY last_use DESC")
    suspend fun getLastUsedAccountId(): Int?

    @Query("SELECT * FROM accounts ORDER BY last_use DESC")
    fun getLastUsedAccount(): Flow<AccountResponse?>
}
