package shov.studio.domain.usecases

import shov.studio.data.repositories.SettingsRepository
import javax.inject.Inject

class GetThemeUseCase @Inject constructor(private val settingsRepository: SettingsRepository) {
    operator fun invoke() = settingsRepository.getTheme()
}
