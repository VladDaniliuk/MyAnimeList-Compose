package shov.studio.domain.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import data.common.repositories.StandardRepository
import data.common.repositories.StandardUserRepository
import shov.studio.domain.core.usecases.GetDetailsNamesUseCase
import shov.studio.domain.core.usecases.GetDetailsUseCase
import shov.studio.domain.core.usecases.GetImagesUseCase
import shov.studio.domain.core.usecases.GetRelatedAnimeUseCase
import shov.studio.domain.core.usecases.GetSearchResultUseCase
import shov.studio.domain.core.usecases.GetUserListPreviewUseCase
import shov.studio.domain.core.usecases.GetUserListUseCase
import shov.studio.domain.core.usecases.GetUserListsPreviewUseCase
import shov.studio.domain.core.usecases.UpdateCommentsUseCase
import shov.studio.domain.core.usecases.UpdateDetailsUseCase
import shov.studio.domain.core.usecases.UpdateIsRewatchingUseCase
import shov.studio.domain.core.usecases.UpdateNumChaptersReadUseCase
import shov.studio.domain.core.usecases.UpdateNumEpisodesWatchedUseCase
import shov.studio.domain.core.usecases.UpdateNumVolumesReadUseCase
import shov.studio.domain.core.usecases.UpdateScoreUseCase
import shov.studio.domain.core.usecases.UpdateStatusUseCase
import shov.studio.domain.core.usecases.UpdateTagsUseCase


@Module
@InstallIn(ViewModelComponent::class)
class StandardUseCaseModule {
    @Provides
    fun provideGetDetailsNamesUseCase(standardRepository: StandardRepository) =
        GetDetailsNamesUseCase(standardRepository)

    @Provides
    fun provideGetDetailsUseCase(standardRepository: StandardRepository) =
        GetDetailsUseCase(standardRepository)

    @Provides
    fun provideGetImagesUseCase(standardRepository: StandardRepository) =
        GetImagesUseCase(standardRepository)

    @Provides
    fun provideGetRelatedAnimeUseCase(standardRepository: StandardRepository) =
        GetRelatedAnimeUseCase(standardRepository)

    @Provides
    fun provideGetSearchResultUseCase(standardRepository: StandardRepository) =
        GetSearchResultUseCase(standardRepository)

    @Provides
    fun provideGetUserListPreviewUseCase(standardRepository: StandardRepository) =
        GetUserListPreviewUseCase(standardRepository)

    @Provides
    fun provideGetUserListsPreviewUseCase(standardRepository: StandardRepository) =
        GetUserListsPreviewUseCase(standardRepository)

    @Provides
    fun provideGetUserListUseCase(standardRepository: StandardRepository) =
        GetUserListUseCase(standardRepository)

    @Provides
    fun provideUpdateCommentsUseCase(standardUserRepository: StandardUserRepository) =
        UpdateCommentsUseCase(standardUserRepository)

    @Provides
    fun provideUpdateDetailsUseCase(standardUserRepository: StandardUserRepository) =
        UpdateDetailsUseCase(standardUserRepository)

    @Provides
    fun provideUpdateIsRewatchingUseCase(standardUserRepository: StandardUserRepository) =
        UpdateIsRewatchingUseCase(standardUserRepository)

    @Provides
    fun provideUpdateNumChaptersReadUseCase(standardUserRepository: StandardUserRepository) =
        UpdateNumChaptersReadUseCase(standardUserRepository)

    @Provides
    fun provideUpdateNumEpisodesWatchedUseCase(standardUserRepository: StandardUserRepository) =
        UpdateNumEpisodesWatchedUseCase(standardUserRepository)

    @Provides
    fun provideUpdateNumVolumesReadUseCase(standardUserRepository: StandardUserRepository) =
        UpdateNumVolumesReadUseCase(standardUserRepository)

    @Provides
    fun provideUpdateScoreUseCase(standardUserRepository: StandardUserRepository) =
        UpdateScoreUseCase(standardUserRepository)

    @Provides
    fun provideUpdateStatusUseCase(standardUserRepository: StandardUserRepository) =
        UpdateStatusUseCase(standardUserRepository)

    @Provides
    fun provideUpdateTagsUseCase(standardUserRepository: StandardUserRepository) =
        UpdateTagsUseCase(standardUserRepository)
}
