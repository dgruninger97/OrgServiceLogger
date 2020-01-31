package edu.rosehulman.orgservicelogger.data

import com.google.firebase.firestore.FirebaseFirestore

val weekDays = listOf("sunday", "monday", "tuesday", "wednesday", "thursday", "friday", "saturday")

fun retrieveEventSeries(seriesId: String, callback: (EventSeries) -> Unit) {
    FirebaseFirestore.getInstance().collection("event_series").document(seriesId).get()
        .addOnSuccessListener {
            callback(it.toObject(EventSeries::class.java)!!)
        }
}

fun retrieveEventOccurrence(eventId: String, callback: (EventOccurrence) -> Unit) {
    FirebaseFirestore.getInstance().collection("event_occurrence").document(eventId).get()
        .addOnSuccessListener {
            callback(it.toObject(EventOccurrence::class.java)!!)
        }
}

fun retrieveEvent(eventId: String, callback: (EventOccurrence, EventSeries) -> Unit) {
    retrieveEventOccurrence(eventId) { event ->
        retrieveEventSeries(event.series) { series ->
            callback(event, series)
        }
    }
}

fun retrieveNotification(notificationId: String, callback: (Notification) -> Unit) {
    FirebaseFirestore.getInstance().collection("notification").document(notificationId).get()
        .addOnSuccessListener {
            callback(it.toObject(Notification::class.java)!!)
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
            callback(it.toObject(Person::class.java)!!)
        }
}

fun writeEventSeries(series: EventSeries) {
    FirebaseFirestore.getInstance().collection("event_series").document(series.id!!).set(series)
}

fun writeEventOccurrence(event: EventOccurrence) {
    FirebaseFirestore.getInstance().collection("event_occurrence").document(event.id!!).set(event)
}

fun writePerson(person:Person){
    FirebaseFirestore.getInstance().collection("person").document(person.id!!).set(person)
}

fun writeOrganization(organization: Organization){
    FirebaseFirestore.getInstance().collection("organization").document(organization.id!!).set(organization)
}

fun writeNotification(notification: Notification){
    FirebaseFirestore.getInstance().collection("notification").document(notification.id!!).set(notification)
}

fun retrieveOrganization(organizationId: String, callback: (Organization) -> Unit){
    FirebaseFirestore.getInstance().collection("organization").document(organizationId).get()
        .addOnSuccessListener {
            callback(it.toObject(Organization::class.java)!!)
        }
}