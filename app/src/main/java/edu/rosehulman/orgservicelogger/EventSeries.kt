package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

class EventSeries(
    var name: String,
    var address: String,
    var description: String,
    var organization: Organization,
    var minPeople: Int,
    var maxPeople: Int,
    var timeStart: Timestamp,
    var timeEnd: Timestamp,
    var weeklyRecurrence: MutableList<Boolean>
) {
    fun formatTimeSpan(): String = formatTimeSpan(timeStart, timeEnd)
}

fun formatTimeSpan(timeStart: Timestamp, timeEnd: Timestamp): String =
    formatTime(timeStart) + "-" + formatTimeAmPm(timeEnd)

private fun formatTime(timeStart: Timestamp): String =
    SimpleDateFormat("h:m").format(timeStart.toDate())

private fun formatTimeAmPm(timeEnd: Timestamp): String =
    SimpleDateFormat("h:ma").format(timeEnd.toDate())
