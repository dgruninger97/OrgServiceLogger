package edu.rosehulman.orgservicelogger.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.EventOccurrence
import edu.rosehulman.orgservicelogger.data.EventSeries
import edu.rosehulman.orgservicelogger.data.retrieveEvent
import edu.rosehulman.orgservicelogger.home.launchFragment
import kotlinx.android.synthetic.main.fragment_event.view.*
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
        }
        return view
    }
}
