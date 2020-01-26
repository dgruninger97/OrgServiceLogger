package edu.rosehulman.orgservicelogger

import com.google.firebase.Timestamp
import java.text.SimpleDateFormat

data class EventInstance(
    val base: EventBase,
    var date: Timestamp,
    val people: List<Person>
) {
    fun formatDate(): String {
        return SimpleDateFormat("EEEE MMM/dd/yyyy").format(date.toDate())
    }
}
