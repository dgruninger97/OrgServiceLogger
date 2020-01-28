package edu.rosehulman.orgservicelogger.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import java.text.SimpleDateFormat

class EventSeries(
    var name: String = "",
    var address: String = "",
    var description: String = "",
    var organization: String = "",
    var minPeople: Int = 0,
    var maxPeople: Int = 0,
    var timeStart: Timestamp = Timestamp(0, 0),
    var timeEnd: Timestamp = Timestamp(0, 0),
    var weeklyRecurrence: MutableMap<String, Boolean> = mutableMapOf() // key = name of day (lowercase), presence dictates if it is recurring
) {
    @get:Exclude
    var id: String? = null

    fun formatTimeSpan(): String = formatTimeSpan(timeStart, timeEnd)
}

fun formatTimeSpan(timeStart: Timestamp, timeEnd: Timestamp): String =
    formatTime(timeStart) + "-" + formatTimeAmPm(timeEnd)

private fun formatTime(timeStart: Timestamp): String =
    SimpleDateFormat("h:mm").format(timeStart.toDate())

private fun formatTimeAmPm(timeEnd: Timestamp): String =
    SimpleDateFormat("h:mma").format(timeEnd.toDate())
