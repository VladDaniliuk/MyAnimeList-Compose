package data.common.converters

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.IllegalFormatWidthException
import java.util.Locale


class DateSerializer : KSerializer<String> {
    override val descriptor: SerialDescriptor = String.serializer().descriptor

    //No sense
    override fun serialize(encoder: Encoder, value: String) {}

    override fun deserialize(decoder: Decoder): String {
        val stringDate = decoder.decodeSerializableValue(String.serializer())

        val sdfDate = when (stringDate.length) {
            LONG_LENGTH -> ZonedDateTime.parse(stringDate, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
                .format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                ?: throw IllegalArgumentException(stringDate)

            FULL_LENGTH -> SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(stringDate)
                ?.let(DateFormat.getDateInstance()::format)
                ?: throw IllegalArgumentException(stringDate)

            DAY_LENGTH -> SimpleDateFormat("yyyy-MM", Locale.getDefault()).parse(stringDate)
                ?.let { date -> SimpleDateFormat("MMMM, yyyy", Locale.getDefault()).format(date) }
                ?: throw IllegalArgumentException(stringDate)

            YEAR_LENGTH -> stringDate
            else -> throw IllegalFormatWidthException(stringDate.length)
        }

        return sdfDate
    }

    companion object {
        const val LONG_LENGTH = 25
        const val FULL_LENGTH = 10
        const val DAY_LENGTH = 7
        const val YEAR_LENGTH = 4
    }
}
