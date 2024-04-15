package shov.studio.domain.auth.usecases

import data.multiaccounts.repositories.AccountsRepository
import javax.inject.Inject

class DeleteAccountUseCase @Inject constructor(private val accountsRepository: AccountsRepository, private val refreshTokenUseCase: RefreshTokenUseCase) {
    suspend operator fun invoke(id: String) {
        accountsRepository.deleteAccount(id)

        refreshTokenUseCase()
    }
}
