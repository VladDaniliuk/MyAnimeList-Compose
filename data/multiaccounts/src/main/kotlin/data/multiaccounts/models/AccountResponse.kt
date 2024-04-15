package data.multiaccounts.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "accounts")
data class AccountResponse(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "last_use") val lastUse: LocalDateTime = LocalDateTime.now(),
)
