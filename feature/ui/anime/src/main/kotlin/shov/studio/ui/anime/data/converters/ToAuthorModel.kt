package shov.studio.ui.anime.data.converters

import shov.studio.domain.core.data.models.AuthorEntity

internal fun AuthorEntity.toAuthorModel() = AuthorModel(firstName, lastName, role)
