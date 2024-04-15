package shov.studio.domain.auth.usecases

import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.repositories.AuthRepository
import javax.inject.Inject

class IsAuthorizedUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountsRepository: AccountsRepository
) {
    suspend operator fun invoke() =
        accountsRepository.getLastUsedAccountId()?.let(authRepository::isSignedIn) ?: false
}
