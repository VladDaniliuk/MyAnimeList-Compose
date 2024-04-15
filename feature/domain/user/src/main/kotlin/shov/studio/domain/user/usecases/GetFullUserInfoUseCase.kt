package shov.studio.domain.user.usecases

import shov.studio.feature.domain.models.data.converters.toEntity
import data.user.models.UserFullResponse
import data.user.repositories.UserRepository
import javax.inject.Inject

class GetFullUserInfoUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.getFull().map(UserFullResponse::toEntity)
}
