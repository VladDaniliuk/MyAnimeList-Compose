package shov.studio.domain.core.data.converters

import data.common.datas.StatusResponse
import shov.studio.domain.core.data.models.StatusEntity

internal fun StatusResponse.toStatusEntity() =
    StatusEntity(watching, completed, onHold, dropped, planToWatch)
