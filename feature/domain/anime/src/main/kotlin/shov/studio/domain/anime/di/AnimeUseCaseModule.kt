package shov.studio.domain.anime.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shov.studio.data.anime.repositories.AnimeRepository
import data.common.repositories.StandardRepository
import shov.studio.domain.anime.usecases.GetAnimeRankingAnimeUseCase
import shov.studio.domain.anime.usecases.GetSeasonUseCase
import shov.studio.domain.anime.usecases.GetSuggestedAnimesUseCase

@Module
@InstallIn(SingletonComponent::class)
class AnimeUseCaseModule {
    @Provides
    fun provideGetAnimeRankingAnimesUseCase(standardRepository: StandardRepository) =
        GetAnimeRankingAnimeUseCase(standardRepository)

    @Provides
    fun provideGetSeasonUseCase(animeRepository: AnimeRepository) =
        GetSeasonUseCase(animeRepository)

    @Provides
    fun provideGetSuggestedAnimeUseCase(animeRepository: AnimeRepository) =
        GetSuggestedAnimesUseCase(animeRepository)
}
