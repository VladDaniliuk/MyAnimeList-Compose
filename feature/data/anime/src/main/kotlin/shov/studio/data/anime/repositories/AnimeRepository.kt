package shov.studio.data.anime.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import shov.studio.enums.Season
import shov.studio.enums.SortType
import shov.studio.data.anime.sources.AnimeSource
import data.common.sources.StandardPagingSource
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource
import javax.inject.Inject

class AnimeRepository @Inject constructor(
    private val animeSource: AnimeSource,
    private val accountPreferencesSource: AccountPreferencesSource,
    private val accountInfoSource: AccountInfoSource,
) {
    fun getSeason(year: Int, season: Season, sort: SortType) = Pager(PagingConfig(10)) {
        StandardPagingSource { key -> animeSource.season(year, season, key, sort) }
    }.flow

    fun getSuggested() = Pager(PagingConfig(10)) {
        StandardPagingSource { key ->
            val accountId = accountInfoSource.getLastUsedAccountId()
                ?: return@StandardPagingSource Result.failure(NullPointerException("Access token is null"))

            accountPreferencesSource.getAccessToken(accountId).fold(
                onSuccess = { token -> animeSource.suggested(key, token) },
                onFailure = { Result.failure(NullPointerException("Access token is null")) }
            )
        }
    }.flow
}
