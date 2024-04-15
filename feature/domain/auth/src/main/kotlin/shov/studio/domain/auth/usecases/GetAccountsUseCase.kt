package shov.studio.domain.auth.usecases

import data.multiaccounts.models.AccountResponse
import data.multiaccounts.repositories.AccountsRepository
import shov.studio.feature.domain.models.data.converters.toEntity
import core.utils.mapResponse
import javax.inject.Inject

class GetAccountsUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
) {
    operator fun invoke() = accountsRepository.getAccounts().mapResponse(AccountResponse::toEntity)
}