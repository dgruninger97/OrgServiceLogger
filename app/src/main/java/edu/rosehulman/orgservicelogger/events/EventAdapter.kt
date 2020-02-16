package edu.rosehulman.orgservicelogger.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.EventOccurrence
import edu.rosehulman.orgservicelogger.data.retrieveEventSeries
import edu.rosehulman.orgservicelogger.event.EventFragment
import edu.rosehulman.orgservicelogger.home.launchFragment

class EventAdapter(
    private val userId: String,
    private val context: FragmentActivity
) : RecyclerView.Adapter<EventViewHolder>() {
    private lateinit var events: List<EventOccurrence>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val holder = EventViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.view_event,
                parent,
                false
            )
        )
        holder.itemView.setOnClickListener {
            launchEvent(holder.adapterPosition)
        }
        return holder
    }

    private fun launchEvent(index: Int) {
        launchFragment(context, EventFragment(userId, events[index].id!!))
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        retrieveEventSeries(event.series) { series ->
            holder.title.text = series.name
            holder.time.text = series.formatTimeSpan()
            holder.people.removeAllViews()
            for (x in 0 until event.people.size) {
                holder.people.addView(context.layoutInflater.inflate(R.layout.view_event_person_present, null))
            }
            for (x in event.people.size until series.minPeople) {
                holder.people.addView(context.layoutInflater.inflate(R.layout.view_event_person_not_present, null))
            }
            event.people
        }
    }

    fun resetTo(es: List<EventOccurrence>) {
        events = es
        notifyDataSetChanged()
    }
}
