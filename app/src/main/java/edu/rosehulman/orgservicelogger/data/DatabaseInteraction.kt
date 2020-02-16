package edu.rosehulman.orgservicelogger.data

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.Constants

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

fun retrieveFutureNotifications(personId: String, callback: (List<Notification>) -> Unit) {
    FirebaseFirestore.getInstance().collection("notification")
        .whereEqualTo("person", personId)
        .whereGreaterThan("time", Timestamp.now())
        .get()
        .addOnSuccessListener { callback(it.toObjects(Notification::class.java)) }
}

fun retrievePerson(personId: String, callback: (Person) -> Unit) {
    FirebaseFirestore.getInstance().collection("person").document(personId).get()
        .addOnSuccessListener {
            Log.d(Constants.TAG, "Getting person $personId")
            callback(it.toObject(Person::class.java)!!)
        }
}

fun writeEventSeries(series: EventSeries) {
    FirebaseFirestore.getInstance().collection("event_series").document(series.id!!).set(series)
}

fun writeEventOccurrence(event: EventOccurrence) {
    FirebaseFirestore.getInstance().collection("event_occurrence").document(event.id!!).set(event)
}

fun writePerson(person: Person) {
    FirebaseFirestore.getInstance().collection("person").document(person.id!!).set(person)
}

fun addInvite(invite: Invite) {
    FirebaseFirestore.getInstance().collection("invite").add(invite)
}

fun writeOrganization(organization: Organization) {
    FirebaseFirestore.getInstance().collection("organization").document(organization.id!!)
        .set(organization)
}

fun writeNotification(notification: Notification) {
    FirebaseFirestore.getInstance().collection("notification").document(notification.id!!)
        .set(notification)
}

fun createOrganization(organization: Organization, callback: (String) -> Unit) {
    FirebaseFirestore.getInstance().collection("organization").add(organization)
        .addOnSuccessListener { callback(it.id) }
}

fun retrieveOrganization(organizationId: String, callback: (Organization) -> Unit) {
    FirebaseFirestore.getInstance().collection("organization").document(organizationId).get()
        .addOnSuccessListener {
            callback(it.toObject(Organization::class.java)!!)
        }
}

fun retrieveOrganizationForPerson(personId: String, callback: (String?) -> Unit) {
    FirebaseFirestore.getInstance().collection("organization")
        .whereEqualTo(FieldPath.of("members", personId), true)
        .limit(1)
        .get()
        .addOnSuccessListener { callback(it.documents.getOrNull(0)?.id) }
}

fun retrievePersonExists(uid: String, callback: (Person?) -> Unit) {
    FirebaseFirestore.getInstance().collection("person")
        .document(uid)
        .get()
        .addOnSuccessListener {
            callback(it.toObject(Person::class.java))
        }
}

fun retrieveInviteExists(email: String, callback: (Invite?) -> Unit) {
    FirebaseFirestore.getInstance().collection("invite")
        .whereEqualTo(FieldPath.of("person", "email"), email)
        .get()
        .addOnSuccessListener {
            callback(it.documents.getOrNull(0)?.toObject(Invite::class.java))
        }
}

fun addMemberToOrganization(organizationId: String, personId: String, isOrganizer: Boolean) {
    FirebaseFirestore.getInstance().collection("organization").document(organizationId)
        .update("members", mapOf(personId to true))
}
