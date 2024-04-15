package shov.studio.domain.core.data.converters

import data.common.datas.AuthorResponse
import shov.studio.domain.core.data.models.AuthorEntity

internal fun AuthorResponse.toAuthorEntity() =
    AuthorEntity(node.firstName, node.lastName, role)
