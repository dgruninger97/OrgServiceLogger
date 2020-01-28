package edu.rosehulman.orgservicelogger.notifications

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import edu.rosehulman.orgservicelogger.R
import edu.rosehulman.orgservicelogger.aden
import edu.rosehulman.orgservicelogger.data.*
import edu.rosehulman.orgservicelogger.event.EventFragment
import edu.rosehulman.orgservicelogger.home.launchFragment
import edu.rosehulman.orgservicelogger.ryvesHallDec12
import edu.rosehulman.orgservicelogger.ryvesHallDec19
import kotlinx.android.synthetic.main.dialog_confirm_event_attendance.view.*
import kotlinx.android.synthetic.main.dialog_edit_event_attendance.view.*
import java.util.concurrent.TimeUnit

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
                    showConfirmDialog(notification)
                }
                is ReminderNotification -> {
                    launchFragment(context, EventFragment(notification.event))
                }
                is NeedsReplacementNotification -> {
                    launchFragment(context, EventFragment(notification.event))
                }
            }
        }
        return holder
    }

    private fun showConfirmDialog(notification: Notification) {
        val dialogView = context.layoutInflater.inflate(
            R.layout.dialog_confirm_event_attendance,
            null
        )
        dialogView.dialog_confirm_event_attendance_event.text =
            formatEvent(notification.event)
        dialogView.dialog_confirm_event_attendance_time.text =
            "Did you attend for the full time?"

        AlertDialog.Builder(context)
            .setTitle("Confirm event attendance")
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                // TODO: set in database
                showConfirmationToast()
            }
            .setNeutralButton("Edit") { _, _ ->
                showEditDialog(notification)
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create().show()
    }

    private fun showEditDialog(notification: Notification) {
        val dialogView = context.layoutInflater.inflate(
            R.layout.dialog_edit_event_attendance,
            null
        )
        dialogView.dialog_edit_event_attendance_event.text =
            formatEvent(notification.event)
        dialogView.dialog_edit_event_attendance_time_text.text =
            "How long did you attend for?"
        dialogView.dialog_edit_event_attendance_time_picker.setIs24HourView(true)
        val endTime = notification.event.series.timeEnd.toDate().time
        val startTime = notification.event.series.timeStart.toDate().time
        val millis = endTime - startTime
        dialogView.dialog_edit_event_attendance_time_picker.currentHour =
            TimeUnit.MILLISECONDS.toHours(millis).toInt()
        dialogView.dialog_edit_event_attendance_time_picker.currentMinute =
            TimeUnit.MILLISECONDS.toMinutes(millis).toInt() % 60

        AlertDialog.Builder(context)
            .setTitle("Edit event attendance")
            .setView(dialogView)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                // TODO: set in database
                showConfirmationToast()
            }
            .setNegativeButton(android.R.string.cancel) { _, _ -> }
            .create().show()
    }

    private fun showConfirmationToast() {
        Toast.makeText(context, "Attendance logged", Toast.LENGTH_SHORT).show()
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

        holder.description.text = formatEvent(notification.event)
    }

    private fun formatEvent(event: EventOccurrence) =
        event.series.name + " " + event.formatDate() + " " + event.series.formatTimeSpan()
}
