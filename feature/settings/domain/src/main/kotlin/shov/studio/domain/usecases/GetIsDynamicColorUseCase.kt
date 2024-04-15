package shov.studio.domain.usecases

import shov.studio.data.repositories.SettingsRepository
import javax.inject.Inject

class GetIsDynamicColorUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    operator fun invoke() = settingsRepository.getIsDynamicColor()
}
