package edu.rosehulman.orgservicelogger.ui.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import edu.rosehulman.orgservicelogger.EventInstance
import edu.rosehulman.orgservicelogger.R
import kotlinx.android.synthetic.main.fragment_event.view.*
import java.text.SimpleDateFormat

class EventFragment(private val event: EventInstance) : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        val day = SimpleDateFormat("EEEE MMM/dd/yyyy").format(event.date.toDate())
        val start = SimpleDateFormat("h:m").format(event.base.timeStart.toDate())
        val end = SimpleDateFormat("h:ma").format(event.base.timeEnd.toDate())
        view.fragment_event_date.text = day + " " + start + "-" + end
        view.fragment_event_description.text = event.base.description
        view.fragment_event_directions.text = event.base.address
        return view
    }
}