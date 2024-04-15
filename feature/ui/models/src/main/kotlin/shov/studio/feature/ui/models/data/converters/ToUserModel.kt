package shov.studio.feature.ui.models.data.converters

import shov.studio.feature.domain.models.data.models.UserEntity
import shov.studio.feature.ui.models.data.models.UserModel

fun UserEntity.toModel() = UserModel(id.toString(), name, picture)
