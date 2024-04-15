package shov.studio.domain.user.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import shov.studio.domain.user.usecases.GetFullUserInfoUseCase
import shov.studio.domain.user.usecases.GetUserInfoUseCase
import data.user.repositories.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
class UserUseCaseModule {
    @Provides
    fun provideGetFullUserInfoUseCase(userRepository: UserRepository) =
        GetFullUserInfoUseCase(userRepository)

    @Provides
    fun provideGetUserInfoUseCase(userRepository: UserRepository) =
        GetUserInfoUseCase(userRepository)
}
