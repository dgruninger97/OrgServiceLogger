package edu.rosehulman.orgservicelogger.events

import com.google.firebase.firestore.FieldPath
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
        .whereEqualTo(FieldPath.of("series", "organization"), organization)
        .orderBy("date", Query.Direction.ASCENDING)
        .get()
        .addOnSuccessListener { snapshot ->
            val seriesCollected = AtomicInteger()
            val events = snapshot.toObjects(EventOccurrence::class.java)
            val series = ArrayList<EventSeries?>()

            for (localEvent in events) {
                series.add(null)
            }

            for ((index, event) in events.withIndex()) {
                FirebaseFirestore.getInstance().collection("event_series").document(event.series)
                    .get()
                    .addOnSuccessListener { seriesSnapshot ->
                        val s = seriesSnapshot.toObject(EventSeries::class.java)!!
                        s.id = seriesSnapshot.id
                        series[index] = s

                        val current = seriesCollected.incrementAndGet()
                        if (current == events.size) {
                            val seriesNonNull = mutableListOf<EventSeries>()
                            series.mapTo(seriesNonNull, { it!! })
                            callback(events, seriesNonNull)
                        }
                    }
            }
        }
}
