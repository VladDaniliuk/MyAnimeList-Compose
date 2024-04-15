package data.multiaccounts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideAccountsRepository(
        accountInfoSource: AccountInfoSource,
        accountPreferencesSource: AccountPreferencesSource
    ): AccountsRepository = AccountsRepository(accountInfoSource, accountPreferencesSource)
}
