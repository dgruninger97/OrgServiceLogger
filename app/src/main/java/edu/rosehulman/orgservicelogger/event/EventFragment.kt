package edu.rosehulman.orgservicelogger.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.*
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.userInfo.UserInfoFragment
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.view_event_attendee.view.*
import kotlinx.android.synthetic.main.view_event_attendee_signup.view.*
import java.text.SimpleDateFormat

class EventFragment(private val userId: String, private val eventId: String) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        retrieveEvent(eventId) { event, series ->
            val day = SimpleDateFormat("EEEE MMM/dd/yyyy").format(event.date.toDate())
            view.fragment_event_name.text = series.name
            view.fragment_event_date.text = day + " " + series.formatTimeSpan()
            view.fragment_event_description.text = series.description
            view.fragment_event_directions.text = series.address
            view.fragment_event_fab.setOnClickListener {
                launchFragment(activity!!, EditEventFragment(event.id!!, null))
            }

            retrieveIsOrganizer(userId, series.organization) { isOrganizer ->
                if (isOrganizer) {
                    view.fragment_event_fab.show()
                }
            }

            loadAttendees(view, event, series)
        }
        return view
    }

    private fun loadAttendees(
        view: View,
        event: EventOccurrence,
        series: EventSeries
    ) {
        if (event.people.isEmpty()) {
            val attendees = view.fragment_event_attendees
            attendees.removeAllViews()
            addEmptySlots(0, series, attendees)
            return
        }

        FirebaseFirestore.getInstance().collection("person")
            .whereIn(FieldPath.documentId(), event.people.keys.toList())
            .get()
            .addOnSuccessListener { snapshot ->
                val attendees = view.fragment_event_attendees
                attendees.removeAllViews()

                val people = snapshot.toObjects(Person::class.java)
                for (person in people) {
                    val view = layoutInflater.inflate(R.layout.view_event_attendee, null, false)
                    view.view_event_attendee_name.text = person.name
                    view.view_event_attendee_name.setOnClickListener {
                        launchFragment(
                            activity!!,
                            UserInfoFragment(userId, person.id!!, series.organization)
                        )
                    }
                    if (!person.canDrive) {
                        view.view_event_attendee_driving_icon.visibility = View.GONE
                    }
                    attendees.addView(view)
                }

                addEmptySlots(people.size, series, attendees)
            }
    }

    private fun addEmptySlots(
        peopleSize: Int,
        series: EventSeries,
        attendees: LinearLayout
    ) {
        for (x in peopleSize until series.maxPeople) {
            val view =
                layoutInflater.inflate(R.layout.view_event_attendee_signup, null, false)
            view.view_event_attendee_signup_button.setOnClickListener {
                signupForEvent()
            }
            attendees.addView(view)
        }
    }

    private fun signupForEvent() {
        Log.d(Constants.TAG, "Signing up $userId for event $eventId")
        addPersonToEvent(userId, eventId)
            .continueWith {
                retrieveEvent(eventId) { event, series ->
                    loadAttendees(view!!, event, series)
                }
            }
        addNotification(Notification.reminder(eventId, userId))
        addNotification(Notification.confirm(eventId, userId))
    }
}
