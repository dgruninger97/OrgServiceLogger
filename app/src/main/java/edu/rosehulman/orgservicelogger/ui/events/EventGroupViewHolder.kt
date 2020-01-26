package edu.rosehulman.orgservicelogger.ui.events

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.view_event_group.view.*

class EventGroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val day = view.view_events_group_day
    val events = view.view_events_group_events
}
