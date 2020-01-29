package edu.rosehulman.orgservicelogger.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.text.SimpleDateFormat

data class EventOccurrence(
    var series: String = "",
    var date: Timestamp = Timestamp(0, 0),
    var people: Map<String, Boolean> = mapOf() // values are always true
) {
    @DocumentId
    var id: String? = null

    fun formatDate(): String = formatDate(date)
}

fun formatDate(date: Timestamp): String = SimpleDateFormat("EEEE MMM/dd/yyyy").format(date.toDate())
