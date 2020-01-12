package edu.rosehulman.orgservicelogger.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import edu.rosehulman.orgservicelogger.R

class EventsFragment : Fragment() {

    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        eventsViewModel =
                ViewModelProviders.of(this).get(EventsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_events, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        eventsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}