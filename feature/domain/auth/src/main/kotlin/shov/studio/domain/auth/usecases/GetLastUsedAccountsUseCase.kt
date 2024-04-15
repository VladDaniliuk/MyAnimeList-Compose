package shov.studio.domain.auth.usecases

import kotlinx.coroutines.flow.map
import data.multiaccounts.models.AccountResponse
import data.multiaccounts.repositories.AccountsRepository
import shov.studio.feature.domain.models.data.converters.toEntity
import javax.inject.Inject

class GetLastUsedAccountsUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
) {
    operator fun invoke() = accountsRepository.getLastUsedAccounts()
        .map { responses -> responses.map(AccountResponse::toEntity) }
}