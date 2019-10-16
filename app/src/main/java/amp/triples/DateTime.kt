package amp.triples

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateTime {

    companion object {

        private var dateTime: LocalDateTime? = null
        private val formatterBaseDate = DateTimeFormatter.ofPattern("yyyyMMdd")
        private val formatterBaseTime = DateTimeFormatter.ofPattern("HHmm")

        private fun nowDateTime() {

            dateTime = LocalDateTime.now()

        }

        fun date(): String? {

            nowDateTime()

            return dateTime?.format(formatterBaseDate)

        }

        fun time(): String? {

            nowDateTime()

            return dateTime?.format(formatterBaseTime)

        }

    }

}