package edu.rosehulman.orgservicelogger.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.Timestamp
import edu.rosehulman.orgservicelogger.*
import edu.rosehulman.orgservicelogger.ui.event.EditEventFragment
import kotlinx.android.synthetic.main.fragment_event.view.*
import kotlinx.android.synthetic.main.fragment_events.view.*
import java.util.*

class EventsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_events, container, false)
        val list = root.fragment_events_list
        list.adapter = EventGroupsAdapter(context!! as FragmentActivity, events)
        list.layoutManager = LinearLayoutManager(context)
        list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        return root
    }
}