package amp.triples

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTime {

    fun date(): String {

        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val date = LocalDate.now()

        return formatter.format(date)

    }

    fun time(): String {

        val formatter = DateTimeFormatter.ofPattern("HH00")
        var time = LocalTime.now()

        time = when (time.hour % 3) {

            0 -> time.plusHours(-1)
            1 -> time.plusHours(-2)
            else -> time

        }

        return formatter.format(time)

    }

}