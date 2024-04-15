package shov.studio.domain.auth.usecases

import data.multiaccounts.repositories.AccountsRepository
import java.time.LocalDateTime
import javax.inject.Inject

class OnUserChangeUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
    private val refreshTokenUseCase: RefreshTokenUseCase,
) {
    suspend operator fun invoke(id: String) {
        accountsRepository
            .updateAccount(accountsRepository.getAccount(id).copy(lastUse = LocalDateTime.now()))

        refreshTokenUseCase()
    }
}