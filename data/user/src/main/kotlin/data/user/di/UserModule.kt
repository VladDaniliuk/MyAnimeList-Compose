package data.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.sources.AccountPreferencesSource
import data.user.repositories.UserRepository
import data.user.sources.UserSource

@Module
@InstallIn(SingletonComponent::class)
class UserModule {
    @Provides
    fun provideUserSource(httpClient: HttpClient) = UserSource(httpClient)

    @Provides
    fun provideUserRepository(
        userSource: UserSource,
        preferencesSource: AccountPreferencesSource,
        accountsRepository: AccountsRepository,
    ) = UserRepository(userSource, preferencesSource, accountsRepository)
}
