package m1k.kotik.negocards.custom_models.date

import java.util.*

class SimpleDate(var year: Int, var month: Int, var day: Int, var separator: String = "-") {
    override fun toString(): String {
        return "${year}${separator}${month}${separator}${day}"
    }
    companion object {
        fun toSimpleDate(date: String, separator: String = "-", newSeparator: String = separator): SimpleDate? {
            val list = date.split(separator)
            try {
                return SimpleDate(list[0].toInt(), list[1].toInt(), list[2].toInt(), newSeparator)
            }
            catch (e: IllegalArgumentException){
                println("Ты передал говно какое-то, а не дату")
            }
            return null
        }
        fun getCurrentDate(): SimpleDate{
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            return SimpleDate(year, month, day)
        }
    }
}