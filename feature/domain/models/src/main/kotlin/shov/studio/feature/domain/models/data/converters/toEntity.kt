package shov.studio.feature.domain.models.data.converters

import data.multiaccounts.models.AccountResponse
import shov.studio.feature.domain.models.data.models.UserEntity

fun AccountResponse.toEntity() = UserEntity(id, name, url)
