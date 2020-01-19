package edu.rosehulman.orgservicelogger.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.EventInstance
import edu.rosehulman.orgservicelogger.R
import java.text.SimpleDateFormat
import java.util.*

class EventGroupsAdapter(
    private val context: FragmentActivity,
    private val events: List<EventInstance>
) :
    RecyclerView.Adapter<EventGroupsViewHolder>() {
    private val eventDays: MutableMap<Date, Int>

    init {
        eventDays = HashMap()
        for ((index, event) in events.withIndex()) {
            val baseDate = baseDate(event.date.toDate())
            if (!eventDays.containsKey(baseDate)) {
                eventDays[baseDate] = index
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventGroupsViewHolder {
        val holder = EventGroupsViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_event_group,
                parent,
                false
            )
        )
        holder.events.adapter = EventAdapter(context)
        holder.events.layoutManager = LinearLayoutManager(context)
        holder.events.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        return holder
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventGroupsViewHolder, position: Int) {
        val event = events[position]
        val date: Date = event.date.toDate()
        holder.day.text = SimpleDateFormat("EEEE MMM/dd/yyyy").format(date)
        val adapter = holder.events.adapter!! as EventAdapter
        adapter.resetTo(
            events.subList(
                eventDays[baseDate(date)]!!,
                advanceDay(eventDays[baseDate(date)]!!)
            )
        )
    }

    private fun advanceDay(startIndex: Int): Int {
        val baseDate = baseDate(events[startIndex].date.toDate())
        for (index in startIndex + 1 until events.size) {
            if (baseDate(events[index].date.toDate()).after(baseDate)) {
                return index
            }
        }
        return events.size
    }

    private fun baseDate(date: Date) = Date(date.year, date.month, date.date)
}
