package shov.studio.domain.auth.usecases

import kotlinx.coroutines.flow.map
import data.multiaccounts.repositories.AccountsRepository
import data.multiaccounts.repositories.AuthRepository
import javax.inject.Inject

class IsAuthorizedFlowUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val accountsRepository: AccountsRepository,
) {
    operator fun invoke() = accountsRepository.getLastUsedAccount().map { response ->
        response?.id?.let(authRepository::isSignedIn) ?: false
    }
}
