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
    fun formatTime(): String {
        val start = SimpleDateFormat("h:m").format(timeStart.toDate())
        val end = SimpleDateFormat("h:ma").format(timeEnd.toDate())
        return start + "-" + end
    }
}
