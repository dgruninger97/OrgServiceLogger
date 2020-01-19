package edu.rosehulman.orgservicelogger.ui.events

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_event.view.*

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val title = view.view_event_title
    val people = view.view_event_people
    val time = view.view_event_time
}
