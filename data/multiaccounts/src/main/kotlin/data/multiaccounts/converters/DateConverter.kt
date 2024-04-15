package data.multiaccounts.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class DateConverter {
    @TypeConverter
    fun longToLocalDateTime(value: Long): LocalDateTime = LocalDateTime.ofEpochSecond(
        value, 0, ZoneId.systemDefault().rules.getOffset(Instant.now())
    )

    @TypeConverter
    fun localDateTimeToLong(date: LocalDateTime) =
        date.toEpochSecond(ZoneId.systemDefault().rules.getOffset(Instant.now()))
}
