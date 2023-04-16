package edu.put.inf151778

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import org.w3c.dom.Text
import java.time.DayOfWeek

class DateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_date)

        val dPicker1 = findViewById<DatePicker>(R.id.datePicker1)
        val dPicker2 = findViewById<DatePicker>(R.id.datePicker2)

        val plainText = findViewById<TextView>(R.id.dText)
        val workingDaysText = findViewById<TextView>(R.id.wdText)

        val pButton = findViewById<Button>(R.id.plusButton)



        dPicker1.init(
            dPicker1.year,
            dPicker1.month,
            dPicker1.dayOfMonth,
            object : DatePicker.OnDateChangedListener{
                override fun onDateChanged(
                    view: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {

                    val date = countDaysBetween(dPicker1, dPicker2) + 1
                    val wdCount = countWorkingDays(dPicker1, dPicker2)
                    plainText.text = date.toString()
                    workingDaysText.text = "Dni roboczych pomiędzy datami: " + wdCount.toString()
                }
            }
        )

        dPicker2.init(
            dPicker2.year,
            dPicker2.month,
            dPicker2.dayOfMonth,
            object : DatePicker.OnDateChangedListener{
                override fun onDateChanged(
                    view: DatePicker?,
                    year: Int,
                    monthOfYear: Int,
                    dayOfMonth: Int
                ) {
                    val date = countDaysBetween(dPicker1, dPicker2) + 1
                    val wdCount = countWorkingDays(dPicker1, dPicker2)
                    plainText.text = date.toString()
                    workingDaysText.text = "Dni roboczych pomiędzy datami: " + wdCount.toString()
                }
            }
        )

        pButton.setOnClickListener{
            val firstDate = LocalDate.of(dPicker1.year, dPicker1.month + 1, dPicker1.dayOfMonth)
            val days = plainText.text.toString().toLong() - 1
            val lastDate = firstDate.plusDays(days)

            dPicker2.updateDate(lastDate.year, lastDate.monthValue - 1, lastDate.dayOfMonth)
        }
    }
}

fun countDaysBetween(startDatePicker: DatePicker, endDatePicker: DatePicker): Long {
    val startDate = LocalDate.of(startDatePicker.year, startDatePicker.month + 1, startDatePicker.dayOfMonth)
    val endDate = LocalDate.of(endDatePicker.year, endDatePicker.month + 1, endDatePicker.dayOfMonth)
    return ChronoUnit.DAYS.between(startDate, endDate)
}

fun countWorkingDays(startDatePicker: DatePicker, endDatePicker: DatePicker): Long{
    val startDate = LocalDate.of(startDatePicker.year, startDatePicker.month + 1, startDatePicker.dayOfMonth)
    val endDate = LocalDate.of(endDatePicker.year, endDatePicker.month + 1, endDatePicker.dayOfMonth)

    var workingDays = 0L
    var date = startDate
    while(date <= endDate){
        if(date.dayOfWeek != DayOfWeek.SATURDAY && date.dayOfWeek != DayOfWeek.SUNDAY){
            if (!(date.monthValue == 1 && (date.dayOfMonth == 1 || date.dayOfMonth == 6))){
                if(!(date.monthValue == 5 && (date.dayOfMonth == 1 || date.dayOfMonth == 3))){
                    if(!(date.monthValue == 8 && date.dayOfMonth == 15)){
                        if(!(date.monthValue == 11 && (date.dayOfMonth == 1 || date.dayOfMonth == 11))){
                            if(!(date.monthValue == 12 && (date.dayOfMonth != 25 || date.dayOfMonth == 26))){
                                val(day, month) = easterDay(date.year)
                                var modifiedMonth = 0
                                var modifiedDay = 0

                                // Sprawdzam czy poniedzialek wielkanocny nie bedzie w kolejnym miesiacu
                                if(day == 31){
                                    modifiedMonth = 4
                                    modifiedDay = 1
                                }
                                else{
                                    modifiedMonth = month
                                    modifiedDay = day + 1
                                }

                                // Obliczanie daty bozego ciala
                                var gbMonth = month
                                var gbDay = day
                                var counter = 60
                                while(counter > 0){
                                    if (gbMonth == 3){
                                        if(gbDay != 31){
                                            gbDay += 1
                                            counter -= 1
                                        }
                                        else {
                                            gbDay = 1
                                            gbMonth = 4
                                            counter -= 1
                                        }
                                    }
                                    else if (gbMonth == 4){
                                        if(gbDay != 30){
                                            gbDay += 1
                                            counter -= 1
                                        }
                                        else {
                                            gbDay = 1
                                            gbMonth = 5
                                            counter -= 1
                                        }
                                    }
                                    else if (gbMonth == 5){
                                        if(gbDay != 31){
                                            gbDay += 1
                                            counter -= 1
                                        }
                                        else {
                                            gbDay = 1
                                            gbMonth = 6
                                            counter -= 1
                                        }
                                    }
                                    else{
                                        gbDay += 1
                                        counter --
                                    }
                                }
                                if(!(date.monthValue == modifiedMonth && date.dayOfMonth == modifiedDay)) {
                                    if(!(date.monthValue == gbMonth && date.dayOfMonth == gbDay)) {
                                        workingDays += 1
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        date = date.plusDays(1)
    }

    var leapDays = 0L
    for(year in startDate.year..endDate.year){
        if(isLeapYear(year)){
            leapDays += 1
        }
    }
    workingDays -= leapDays

    return workingDays
}

fun isLeapYear(year: Int): Boolean{
    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)
}

fun easterDay(year: Int): Pair<Int, Int>{
    val a = year % 19
    val b = year / 100
    val c = year % 100
    val d = b / 4
    val e = b % 4
    val f = ((b + 8) / 25)
    val g = ((b - f + 1) / 3)
    val h = (19 * a + b - d - g + 15) % 30
    val i = c / 4
    val k = c % 4
    val l = (32 + 2 * e + 2 * i - h - k) % 7
    val m = ((a + 11 * h + 22 * l) / 451)
    val p = (h + l - 7 * m + 114) % 31
    val day = p + 1
    val month = (h + l - 7 * m + 114) / 31
    return Pair(day, month)
}