package shov.studio.domain.core.data.converters

import data.common.datas.DataStandardResponse
import shov.studio.domain.core.data.models.NodeStandardEntity

fun DataStandardResponse.toNodeStandardEntity() = NodeStandardEntity(
    id = node.id,
    title = node.title,
    picture = node.mainPicture?.medium,
    mean = node.mean,
    synopsis = node.synopsis
)
