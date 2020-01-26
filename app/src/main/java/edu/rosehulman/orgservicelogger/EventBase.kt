package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

data class EventBase(
    val name: String,
    val address: String,
    val description: String,
    val organization: Organization,
    val minPeople: Int,
    val maxPeople: Int,
    var timeStart: Timestamp,
    var timeEnd: Timestamp,
    val weeklyRecurrence: List<Boolean>
) {
    fun formatTimeSpan(): String = formatTimeSpan(timeStart, timeEnd)
}

fun formatTimeSpan(timeStart: Timestamp, timeEnd: Timestamp): String =
    formatTime(timeStart) + "-" + formatTimeAmPm(timeEnd)

private fun formatTime(timeStart: Timestamp) =
    SimpleDateFormat("h:m").format(timeStart.toDate())

private fun formatTimeAmPm(timeEnd: Timestamp) =
    SimpleDateFormat("h:ma").format(timeEnd.toDate())
