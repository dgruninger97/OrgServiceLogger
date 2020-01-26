package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

data class EventInstance(
    var base: EventBase,
    var date: Timestamp,
    var people: List<Person>
) {
    fun formatDate(): String = formatDate(date)
}

fun formatDate(date: Timestamp) = SimpleDateFormat("EEEE MMM/dd/yyyy").format(date.toDate())
