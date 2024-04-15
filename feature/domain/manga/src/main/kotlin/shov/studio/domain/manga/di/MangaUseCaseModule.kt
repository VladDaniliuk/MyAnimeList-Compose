package shov.studio.domain.manga.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import data.common.repositories.StandardRepository
import shov.studio.domain.manga.usecases.GetMangaRankingAnimeUseCase

@Module
@InstallIn(SingletonComponent::class)
object MangaUseCaseModule {
    @Provides
    fun provideGetMangaRankingMangasUseCase(standardRepository: StandardRepository) =
        GetMangaRankingAnimeUseCase(standardRepository)
}
