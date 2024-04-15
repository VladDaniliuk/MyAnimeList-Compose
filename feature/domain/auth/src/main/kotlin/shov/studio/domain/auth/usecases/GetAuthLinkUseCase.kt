package shov.studio.domain.auth.usecases

import data.multiaccounts.repositories.AuthRepository
import javax.inject.Inject

class GetAuthLinkUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke() = authRepository.getAuthLink()
}
