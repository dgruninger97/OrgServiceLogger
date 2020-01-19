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
import kotlinx.android.synthetic.main.fragment_events.view.*
import java.util.*

class EventsFragment() : Fragment() {
    private val lambdaChi = Organization("Lambda Chi Alpha", listOf(), listOf(), listOf(), 4)
    private val ryvesHallBase = EventBase(
        "Ryves Hall",
        "1356 Locust St, Terre Haute, IN 47807",
        "Volunteer at the local Ryves Hall youth center.  This often involves light physical activity such as installing wiring, setting up for events, or painting.",
        lambdaChi,
        2,
        2,
        Timestamp(Date(119, 11, 12, 17, 30, 0)), // 12/19/2019 5:30:00pm
        Timestamp(Date(119, 11, 12, 19, 30, 0)), // 12/19/2019 7:30:00pm,
        listOf(false, false, false, false, true, false, false) // Sunday, ..., Saturday
    )
    private val chris = Person(
        arrayListOf(lambdaChi),
        arrayListOf(),
        "Chris",
        "Gregory",
        "gregorcj@rose-hulman.edu",
        "541 740 7370",
        true,
        arrayListOf(),
        arrayListOf()
    )
    private val david = Person(
        arrayListOf(lambdaChi),
        arrayListOf(),
        "David",
        "Gruninger",
        "grunindm@rose-hulman.edu",
        "317 605 5636",
        true,
        arrayListOf(),
        arrayListOf()
    )
    private val events = listOf(
        EventInstance(
            ryvesHallBase,
            Timestamp(Date(119, 11, 12)),
            arrayListOf(chris, david)
        ).also { chris.events.add(it); david.events.add(it) },
        EventInstance(
            ryvesHallBase,
            Timestamp(Date(119, 11, 19)),
            arrayListOf(chris)
        ).also { chris.events.add(it) }
    )

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