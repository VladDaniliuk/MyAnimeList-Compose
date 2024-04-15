package shov.studio.domain.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import shov.studio.data.repositories.SettingsRepository
import shov.studio.domain.usecases.GetIsDynamicColorUseCase
import shov.studio.domain.usecases.GetThemeUseCase
import shov.studio.domain.usecases.SetIsDynamicColorUseCase
import shov.studio.domain.usecases.SetThemeUseCase

@Module
@InstallIn(SingletonComponent::class)
class SettingsUseCaseModule {
    @Provides
    fun provideGetIsDynamicColorUseCase(
        settingsRepository: SettingsRepository,
    ): GetIsDynamicColorUseCase = GetIsDynamicColorUseCase(settingsRepository)

    @Provides
    fun provideGetThemeUseCase(settingsRepository: SettingsRepository): GetThemeUseCase =
        GetThemeUseCase(settingsRepository)

    @Provides
    fun provideSetIsDynamicColorUseCase(
        settingsRepository: SettingsRepository,
    ): SetIsDynamicColorUseCase = SetIsDynamicColorUseCase(settingsRepository)

    @Provides
    fun provideSetThemeUseCase(settingsRepository: SettingsRepository): SetThemeUseCase =
        SetThemeUseCase(settingsRepository)
}
