package shov.studio.feature.domain.models.data.converters

import shov.studio.feature.domain.models.data.models.UserEntity
import data.user.models.UserResponse

fun UserResponse.toUserEntity() = UserEntity(id, name, picture)
