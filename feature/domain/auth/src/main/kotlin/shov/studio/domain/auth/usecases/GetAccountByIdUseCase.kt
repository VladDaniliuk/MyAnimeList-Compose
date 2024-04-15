package shov.studio.domain.auth.usecases

import data.multiaccounts.repositories.AccountsRepository
import shov.studio.feature.domain.models.data.converters.toEntity
import javax.inject.Inject

class GetAccountByIdUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
) {
    suspend operator fun invoke(id: String) = accountsRepository.getAccount(id).toEntity()
}
