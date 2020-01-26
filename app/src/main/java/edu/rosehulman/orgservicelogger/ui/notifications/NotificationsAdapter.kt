package edu.rosehulman.orgservicelogger.ui.notifications

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.*
import edu.rosehulman.orgservicelogger.ui.event.EventFragment

class NotificationsAdapter(private val context: FragmentActivity) :
    RecyclerView.Adapter<NotificationViewHolder>() {

    private val notifications = arrayListOf(
        NeedsReplacementNotification(ryvesHallDec19, aden),
        ConfirmNotification(ryvesHallDec12),
        ReminderNotification(ryvesHallDec12)
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.view_notification, parent, false)
        val holder = NotificationViewHolder(view)
        view.setOnClickListener {
            val notification = notifications[holder.adapterPosition]
            when (notification) {
                is ConfirmNotification -> {
                    TODO()
                }
                is ReminderNotification -> {
                    launchFragment(context, EventFragment(notification.event))
                }
                is NeedsReplacementNotification -> {
                    TODO()
                }
            }
        }
        return holder
    }

    override fun getItemCount() = notifications.size

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notifications[position]
        when (notification) {
            is ConfirmNotification -> {
                holder.icon.setImageResource(R.drawable.ic_notification_confirm)
                holder.title.setText(R.string.text_notification_confirm)
            }
            is ReminderNotification -> {
                holder.icon.setImageResource(R.drawable.ic_notification_reminder)
                holder.title.setText(R.string.text_notification_reminder)
            }
            is NeedsReplacementNotification -> {
                holder.icon.setImageResource(R.drawable.ic_notification_replacement)
                holder.title.setText(
                    context.getString(R.string.text_notification_replacement).format(
                        notification.person.name
                    )
                )
            }
        }

        holder.description.setText(
            notification.event.base.name + " " + notification.event.formatDate() + " " + notification.event.base.formatTimeSpan()
        )
    }
}
