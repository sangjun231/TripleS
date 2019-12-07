package amp.triples

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateTime {

    fun baseTime(): String {

        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH00")
        var dateTime = LocalDateTime.now()

        dateTime = when (dateTime.hour % 3) {

            0 -> dateTime.plusHours(-1)
            1 -> dateTime.plusHours(-2)
            else -> dateTime

        }

        return formatter.format(dateTime)

    }

    fun baseTime2(): String {

        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH00")
        var dateTime = LocalDateTime.now()

        return formatter.format(dateTime)

    }

    fun now(): String {

        val formatter = DateTimeFormatter.ofPattern("yyyyMMddHH00")
        var dateTime = LocalDateTime.now()

        dateTime = when (dateTime.hour % 3) {

            0 -> dateTime
            1 -> dateTime.plusHours(-1)
            else -> dateTime.plusHours(-2)

        }

        return formatter.format(dateTime)

    }

}