package shov.studio.domain.usecases

import shov.studio.core.data.Theme
import shov.studio.data.repositories.SettingsRepository
import javax.inject.Inject

class SetThemeUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    suspend operator fun invoke(theme: Theme) {
        settingsRepository.setTheme(theme)
    }
}
