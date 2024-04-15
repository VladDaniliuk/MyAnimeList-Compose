package shov.studio.domain.usecases

import shov.studio.data.repositories.SettingsRepository
import javax.inject.Inject

class SetIsDynamicColorUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(isDynamicColor: Boolean) {
        settingsRepository.setIsDynamicColor(isDynamicColor)
    }
}
