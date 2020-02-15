package edu.rosehulman.orgservicelogger.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.data.Notification

class NotificationsAdapter(private val context: FragmentActivity) :
    RecyclerView.Adapter<NotificationViewHolder>() {
    private var notifications = listOf<Notification>()

    fun setNotifications(notifications: List<Notification>) {
        this.notifications = notifications
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_notification, parent, false)
        val holder = NotificationViewHolder(view)
        view.setOnClickListener {
            val notification = notifications[holder.adapterPosition]
            NotificationAction.performNotification(context, notification)
        }
        return holder
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]

        holder.icon.setImageResource(notification.getIconRes())

        if (notification.type == Notification.TYPE_NEEDS_REPLACEMENT) {
            holder.title.text = context.getString(R.string.text_notification_replacement_default)
        }
        notification.getTitle(context) { holder.title.text = it }

        notification.getDescription { holder.description.text = it }
    }
}
