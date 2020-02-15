package edu.rosehulman.orgservicelogger.event

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import edu.rosehulman.orgservicelogger.Constants
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.*
import edu.rosehulman.orgservicelogger.home.launchFragment
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.view_event_attendee.view.*
import kotlinx.android.synthetic.main.view_event_attendee_signup.view.*
import java.text.SimpleDateFormat

class EventFragment(private val eventId: String) : Fragment() {
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
                launchFragment(activity!!, EditEventFragment(event.id!!))
            }

            val attendees = view.fragment_event_attendees
            FirebaseFirestore.getInstance().collection("person")
                .whereIn(FieldPath.documentId(), event.people.keys.toList())
                .get()
                .addOnSuccessListener { snapshot ->
                    val people = snapshot.toObjects(Person::class.java)
                    for (person in people) {
                        val view = layoutInflater.inflate(R.layout.view_event_attendee, null, false)
                        view.view_event_attendee_name.text = person.name
                        if (!person.canDrive) {
                            view.view_event_attendee_driving_icon.visibility = View.GONE
                        }
                        attendees.addView(view)
                    }

                    for (x in people.size until series.maxPeople) {
                        val view =
                            layoutInflater.inflate(R.layout.view_event_attendee_signup, null, false)
                        view.view_event_attendee_signup_button.setOnClickListener {
                            val personId = FirebaseAuth.getInstance().currentUser!!.uid
                            Log.d(Constants.TAG, "Signing up $personId for event $eventId")
                            TODO()
                        }
                        attendees.addView(view)
                    }
                }
        }
        return view
    }
}
