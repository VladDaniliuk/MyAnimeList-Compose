package shov.studio.data.anime.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import shov.studio.data.anime.repositories.AnimeRepository
import shov.studio.data.anime.sources.AnimeSource
import data.multiaccounts.sources.AccountInfoSource
import data.multiaccounts.sources.AccountPreferencesSource
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AnimeModule {
    @Singleton
    @Provides
    fun provideAnimeRepository(
        animeSource: AnimeSource,
        preferencesSource: AccountPreferencesSource,
        accountInfoSource: AccountInfoSource,
    ) = AnimeRepository(animeSource, preferencesSource, accountInfoSource)

    @Singleton
    @Provides
    fun provideAnimeSource(client: HttpClient): AnimeSource = AnimeSource(client)
}
