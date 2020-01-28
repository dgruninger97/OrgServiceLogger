package edu.rosehulman.orgservicelogger.data

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.model.value.ReferenceValue
import java.text.SimpleDateFormat

data class EventOccurrence(
    var series: String = "",
    var date: Timestamp = Timestamp(0, 0),
    var people: Map<Person, Boolean> = mapOf() // values are always true
) {
    @get:Exclude
    var id: String? = null

    fun formatDate(): String = formatDate(date)
}

fun formatDate(date: Timestamp): String = SimpleDateFormat("EEEE MMM/dd/yyyy").format(date.toDate())
