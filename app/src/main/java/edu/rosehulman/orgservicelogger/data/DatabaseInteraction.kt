package edu.rosehulman.orgservicelogger.data

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.Constants

val weekDays = listOf("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")

fun retrieveEventSeries(seriesId: String, callback: (EventSeries) -> Unit) {
    FirebaseFirestore.getInstance().collection("event_series").document(seriesId).get()
        .addOnSuccessListener {
            val series = it.toObject(EventSeries::class.java)!!
            series.id = seriesId
            callback(series)
        }
}

fun retrieveEventOccurrence(eventId: String, callback: (EventOccurrence) -> Unit) {
    FirebaseFirestore.getInstance().collection("event_occurrence").document(eventId).get()
        .addOnSuccessListener {
            val event = it.toObject(EventOccurrence::class.java)!!
            event.id = eventId
            callback(event)
        }
}

fun retrieveEvent(eventId: String, callback: (EventOccurrence, EventSeries) -> Unit) {
    retrieveEventOccurrence(eventId) { event ->
        retrieveEventSeries(event.series) { series ->
            callback(event, series)
        }
    }
}

fun retrieveNotifications(personId: String, callback: (List<Notification>) -> Unit) {
    FirebaseFirestore.getInstance().collection("notification")
        .whereEqualTo("person", personId).get()
        .addOnSuccessListener { callback(it.toObjects(Notification::class.java)) }
}

fun retrievePerson(personId: String, callback: (Person) -> Unit) {
    FirebaseFirestore.getInstance().collection("person").document(personId).get()
        .addOnSuccessListener {
            val person = it.toObject(Person::class.java)!!
            person.id = personId
            callback(person)
        }
}

fun writeEventSeries(series: EventSeries) {
    FirebaseFirestore.getInstance().collection("event_series").document(series.id!!).set(series)
}

fun writeEventOccurrence(event: EventOccurrence) {
    FirebaseFirestore.getInstance().collection("event_occurrence").document(event.id!!).set(event)
}
