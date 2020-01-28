package edu.rosehulman.orgservicelogger.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.EventOccurrence
import edu.rosehulman.orgservicelogger.event.EventFragment
import edu.rosehulman.orgservicelogger.home.launchFragment

class EventAdapter(
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
        launchFragment(context, EventFragment(events[index].id!!))
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.title.text = event.series.name
        holder.time.text = event.series.formatTimeSpan()
    }

    fun resetTo(es: List<EventOccurrence>) {
        events = es
        notifyDataSetChanged()
    }
}
