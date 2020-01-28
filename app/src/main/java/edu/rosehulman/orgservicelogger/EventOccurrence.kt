package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

data class EventOccurrence(
    var series: EventSeries,
    var date: Timestamp,
    var people: List<Person>
) {
    fun formatDate(): String = formatDate(date)
}

fun formatDate(date: Timestamp): String = SimpleDateFormat("EEEE MMM/dd/yyyy").format(date.toDate())
