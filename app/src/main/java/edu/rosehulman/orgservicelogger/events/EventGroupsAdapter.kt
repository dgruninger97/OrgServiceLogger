package edu.rosehulman.orgservicelogger.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.EventOccurrence
import edu.rosehulman.orgservicelogger.data.EventSeries
import java.text.SimpleDateFormat
import java.util.*

class EventGroupsAdapter(
    private val context: FragmentActivity
) :
    RecyclerView.Adapter<EventGroupViewHolder>() {
    private var events = listOf<EventOccurrence>()
    private var serieses = listOf<EventSeries>()
    private var eventDays = mutableMapOf<Date, Int>()

    fun resetTo(events: List<EventOccurrence>, serieses: List<EventSeries>) {
        this.events = events
        this.serieses = serieses

        eventDays = HashMap()
        for ((index, event) in events.withIndex()) {
            val baseDate = baseDate(event.date.toDate())
            if (!eventDays.containsKey(baseDate)) {
                eventDays[baseDate] = index
            }
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventGroupViewHolder {
        val holder = EventGroupViewHolder(
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

    override fun onBindViewHolder(holder: EventGroupViewHolder, position: Int) {
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
