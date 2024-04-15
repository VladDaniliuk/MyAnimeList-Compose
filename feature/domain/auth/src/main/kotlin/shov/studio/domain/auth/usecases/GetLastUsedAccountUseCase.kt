package shov.studio.domain.auth.usecases

import kotlinx.coroutines.flow.mapNotNull
import data.multiaccounts.repositories.AccountsRepository
import shov.studio.feature.domain.models.data.converters.toEntity
import javax.inject.Inject

class GetLastUsedAccountUseCase @Inject constructor(
    private val accountsRepository: AccountsRepository,
) {
    operator fun invoke() =
        accountsRepository.getLastUsedAccount().mapNotNull { response -> response?.toEntity() }
}
