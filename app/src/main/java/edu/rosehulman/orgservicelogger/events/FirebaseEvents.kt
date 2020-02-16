package edu.rosehulman.orgservicelogger.events

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import edu.rosehulman.orgservicelogger.data.EventOccurrence
import edu.rosehulman.orgservicelogger.data.EventSeries
import java.util.concurrent.atomic.AtomicInteger

fun retrieveEventsForOrganization(
    organization: String,
    callback: (MutableList<EventOccurrence>, MutableList<EventSeries>) -> Unit
) {
    FirebaseFirestore.getInstance().collection("event_occurrence")
        .orderBy("date", Query.Direction.ASCENDING)
        .get()
        .addOnSuccessListener { snapshot ->
            val seriesCollected = AtomicInteger()

            val events = snapshot.toObjects(EventOccurrence::class.java)
            val series = mutableListOf<EventSeries?>()
            for (localEvent in events) {
                series.add(null)
            }
            for ((index, event) in events.withIndex()) {
                FirebaseFirestore.getInstance().collection("event_series").document(event.series)
                    .get()
                    .addOnSuccessListener { seriesSnapshot ->
                        val s = seriesSnapshot.toObject(EventSeries::class.java)!!
                        if (s.organization == organization) {
                            series[index] = s
                        }
                        val current = seriesCollected.incrementAndGet()
                        if (current == events.size) {
                            val occurrences = mutableListOf<EventOccurrence>()
                            val seriesNonNull = mutableListOf<EventSeries>()
                            for (index in series.indices) {
                                if (series[index] != null) {
                                    occurrences.add(events[index])
                                    seriesNonNull.add(series[index]!!)
                                }
                            }
                            callback(occurrences, seriesNonNull)
                        }
                    }
            }
        }
}
