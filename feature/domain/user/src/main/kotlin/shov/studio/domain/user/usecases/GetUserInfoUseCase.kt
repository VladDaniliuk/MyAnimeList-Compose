package shov.studio.domain.user.usecases

import shov.studio.feature.domain.models.data.converters.toUserEntity
import data.user.models.UserResponse
import data.user.repositories.UserRepository
import javax.inject.Inject

class GetUserInfoUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke() = userRepository.get().map(UserResponse::toUserEntity)
}
