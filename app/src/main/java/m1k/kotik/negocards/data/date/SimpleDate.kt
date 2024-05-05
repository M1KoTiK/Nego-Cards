package m1k.kotik.negocards.data.date

import java.util.*

class SimpleDate(var year: String, var month: String, var day: String, var minute: String? = null, var separatorForDate: String = "-", var separatorForTime: String = ":") {

    override fun toString(): String {
        return "${day}${separatorForDate}${month}${separatorForDate}${year}"
    }

    companion object {
        fun toSimpleDate(date: String, separator: String = "-", newSeparator: String = separator): SimpleDate? {
            val list = date.split(separator)
            try {
                return SimpleDate(list[0], list[1], list[2], newSeparator)
            }
            catch (e: IllegalArgumentException){
                println("Ты передал г#в#о какое-то, а не дату")
            }
            return null
        }

        fun getCurrentDate(): SimpleDate {
            fun toNormalMonthOrDayFormatting(numberOfMonthOrDay: Int): String{
                if(numberOfMonthOrDay < 10){
                    return "0${numberOfMonthOrDay}"
                }
                else{
                    return numberOfMonthOrDay.toString()
                }
            }
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR).toString()
            val month = toNormalMonthOrDayFormatting(c.get(Calendar.MONTH)) + 1
            var day = toNormalMonthOrDayFormatting(c.get(Calendar.DAY_OF_MONTH))
            val hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val second = c.get(Calendar.SECOND)
            return SimpleDate(year, month, day)
        }
    }
}