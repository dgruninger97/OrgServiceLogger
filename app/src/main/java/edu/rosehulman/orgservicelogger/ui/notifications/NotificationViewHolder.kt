package edu.rosehulman.orgservicelogger.ui.notifications

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notification_view.view.*

class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val icon = itemView.icon
    val title = itemView.title
    val description = itemView.description
}
