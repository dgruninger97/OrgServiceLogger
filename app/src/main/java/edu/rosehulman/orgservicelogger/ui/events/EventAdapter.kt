package edu.rosehulman.orgservicelogger.ui.events

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.EventInstance
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.ui.event.EventFragment
import edu.rosehulman.orgservicelogger.ui.home.launchFragment

class EventAdapter(
    private val context: FragmentActivity
) : RecyclerView.Adapter<EventViewHolder>() {
    private lateinit var events: List<EventInstance>

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
        launchFragment(context, EventFragment(events[index]))
    }

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]
        holder.title.text = event.base.name
        holder.time.text = event.base.formatTimeSpan()
    }

    fun resetTo(es: List<EventInstance>) {
        events = es
        notifyDataSetChanged()
    }
}
