package shov.studio.domain.core.data.models

data class RelatedModelEntity(
    val id: Int,
    val title: String,
    val picture: String?,
    val rating: Float?,
    val synopsis: String,
)
