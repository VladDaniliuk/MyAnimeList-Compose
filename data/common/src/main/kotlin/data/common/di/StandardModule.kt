package data.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import data.common.repositories.StandardRepository
import data.common.repositories.StandardUserRepository
import data.common.sources.StandardSource
import data.common.sources.StandardUserSource
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource

@Module
@InstallIn(SingletonComponent::class)
class StandardModule {
    @Provides
    fun provideStandardSource(httpClient: HttpClient) = StandardSource(httpClient)

    @Provides
    fun provideStandardRepository(
        standardSource: StandardSource,
        preferencesSource: AccountPreferencesSource,
        infoSource: AccountInfoSource,
    ) = StandardRepository(standardSource, preferencesSource, infoSource)

    @Provides
    fun provideStandardUserSource(client: HttpClient) = StandardUserSource(client)

    @Provides
    fun provideStandardUserRepository(
        standardUserSource: StandardUserSource,
        preferencesSource: AccountPreferencesSource,
        infoSource: AccountInfoSource,
    ) = StandardUserRepository(standardUserSource, preferencesSource, infoSource)
}
